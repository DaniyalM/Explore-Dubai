package com.app.dubaiculture.ui.postLogin.events.detail

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventDetailBinding
import com.app.dubaiculture.databinding.FragmentRegisterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.EventDetailViewModel
import com.app.dubaiculture.ui.postLogin.events.adapters.EventScheduleItems
import com.app.dubaiculture.utils.Constants.NavBundles.EVENT_OBJECT
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import kotlinx.android.synthetic.main.event_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.event_detail_schedule_layout.view.*
import kotlinx.android.synthetic.main.fragment_event_detail.view.*
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.view.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class EventDetailFragment : BaseFragment<FragmentEventDetailBinding>(),
    OnMapReadyCallback, View.OnClickListener {
    private val eventDetailViewModel: EventDetailViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager
    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var locationRequest: LocationRequest
    @Inject
    lateinit var locationHelper: LocationHelper

    var loc = Location("dummyprovider")
    lateinit var scheduleAdapter: GroupAdapter<GroupieViewHolder>
    private var eventObj = Events()
    private val textToSpeechEngine: TextToSpeech by lazy {
        // Pass in context and the listener.
        TextToSpeech(requireContext()) { status ->
            // set our locale only if init was success.
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(getCurrentLanguage().language)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) :FragmentEventDetailBinding{
//        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds).setDuration(500)
//        sharedElementReturnTransition =  TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds).setDuration(500)
        return FragmentEventDetailBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        locationPermission()
        arguments?.let {
            eventObj = it.getParcelable<Events>(EVENT_OBJECT)!!
        }
        subscribeUiEvents(eventDetailViewModel)

        mapSetUp()
        uiActions()
        rvSetUp()

        binding.root.btn_register_now.setOnClickListener {

        }
        binding.root.tv_direction.setOnClickListener {

        }
        binding.root.ll_callus.setOnClickListener {
            openDiallerBox("123123123")

        }
        binding.root.ll_email_us.setOnClickListener {
            openEmailbox("test@gmail.com")
        }


        binding.root.imgFb.setOnClickListener {

        }

        binding.root.imgTwitter.setOnClickListener {

        }


        binding.root.imgInsta.setOnClickListener {

        }


        binding.root.imgUtube.setOnClickListener {

        }


        binding.root.imgUtube.setOnClickListener {

        }

        binding.root.imgLinkedin.setOnClickListener {

        }

        binding.root.rbEventInfo.setOnClickListener {
            binding.root.ll_even_info.visibility = View.VISIBLE
            binding.root.ll_schedule.visibility = View.GONE
        }
        binding.root.rbSchedule.setOnClickListener {
            binding.root.ll_even_info.visibility = View.GONE
            binding.root.ll_schedule.visibility = View.VISIBLE
        }
    }


    private fun uiActions() {
        binding.apply {
            root.apply {
                title.text = eventObj.title
                tv_title.text = eventObj.title
                tv_location.text = eventObj.locationTitle
                tv_event_days_date.text = "${eventObj.toDate}- ${eventObj.fromDate} ${eventObj.fromMonthYear}  |  ${eventObj.fromDay} - ${eventObj.toDay}"
                tv_times.text = "${eventObj.fromTime} - ${eventObj.toTime}"
                tv_category.text = eventObj.category
                category.text = eventObj.category
                tv_event_date.text = dateFormat(eventObj.dateFrom)
                glide.load(BuildConfig.BASE_URL + eventObj.image).into(imageView)
            }
        }
        binding.root.btn_reg.setOnClickListener(this)
        binding.root.back.setOnClickListener(this)
        binding.root.back_event.setOnClickListener(this)
        binding.root.tv_swipe_up_event.setOnClickListener(this)
        binding.root.img_share_event.setOnClickListener(this)
        binding.root.bookingCalender_event.setOnClickListener(this)
        binding.root.favourite_event.setOnClickListener(this)
        binding.root.favourite.setOnClickListener(this)
        binding.root.img_event_speaker.setOnClickListener(this)



        binding.apply {
            appbarEventnDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (verticalOffset == -binding.root.collapsingToolbarEventDetail.height + binding.root.toolbarEventDetail.height) {
                    Timber.e(verticalOffset.toString())
                    //toolbar is collapsed here
                    //write your code here
                    defaultCloseToolbar.visibility = View.VISIBLE
//                    img.visibility = View.VISIBLE
                    imageView4.visibility = View.VISIBLE
                } else {
                    defaultCloseToolbar.visibility = View.GONE
                    imageView4.visibility = View.GONE
//                    img.visibility = View.GONE
                }
            })

        }
    }

    private fun rvSetUp() {
        binding.root.rv_event_up_coming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
        }

        scheduleAdapter = GroupAdapter()
        binding.root.rvSchedule.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = scheduleAdapter
            scheduleAdapter.apply {
                add(
                    EventScheduleItems(
                        todate = "14",
                        monthYear = "NOV, 15",
                        day = "Sunday",
                        startTime = "6PM - 7PM",
                        startTimeDesc = "Various artistic and cultural activities",
                        endTime = "8PM - 9PM",
                        endTimeDsec = "Opening show ‘The Wise Poet’ a theatrical performance by Al Ahli Dubai Theatre"
                    )
                )
                add(
                    EventScheduleItems(
                        todate = "22",
                        monthYear = "NOV, 22",
                        day = "Monday",
                        startTime = "6PM - 7PM",
                        startTimeDesc = "Various artistic and cultural activities",
                        endTime = "8PM - 9PM",
                        endTimeDsec = "Opening show ‘The Wise Poet’ a theatrical performance by Al Ahli Dubai Theatre"
                    )
                )

                add(
                    EventScheduleItems(
                        todate = "15",
                        monthYear = "NOV, 21",
                        day = "Tuesday",
                        startTime = "6PM - 7PM",
                        startTimeDesc = "Various artistic and cultural activities",
                        endTime = "8PM - 9PM",
                        endTimeDsec = "Opening show ‘The Wise Poet’ a theatrical performance by Al Ahli Dubai Theatre"
                    )
                )
            }
        }
    }

    private fun mapSetUp() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap?) {
        if(eventObj.latitude!!.isNotEmpty()) {
            val trafficDigitalLatLng =
                LatLng((eventObj.latitude!!.toDouble()), eventObj.longitude!!.toDouble())

            map?.addMarker(MarkerOptions()
                .position(trafficDigitalLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location)))!!
                .title = eventObj.title
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    trafficDigitalLatLng, 14.0f
                )
            )
            map.cameraPosition.target
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_event_speaker -> {
                binding.root.tv_desc_readmore.text = "Get today's headlines and news you need to know from Washington and around the world. Get unlimited access to breaking news, trusted coverage, and expert analysis. Get Newsletters. Download App. View Events."
                if (binding.root.tv_desc_readmore.text.isNotEmpty()) {
                    textToSpeechEngine.speak( binding.root.tv_desc_readmore.text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
                }
            }
            R.id.btn_reg -> {
                navigate(R.id.action_eventDetailFragment2_to_registerNowFragment)
            }
            R.id.favourite -> {
                navigate(R.id.action_eventDetailFragment2_to_postLoginFragment)
            }
            R.id.back -> {
                back()
            }
            R.id.back_event -> {
                back()
            }
            R.id.tv_swipe_up_event -> {
                eventDetailViewModel.showToast("Swipe up")
            }
            R.id.img_share_event -> {
                eventDetailViewModel.showToast("Share")
            }
            R.id.bookingCalender_event -> {
                eventDetailViewModel.showToast("Calender")
            }
            R.id.favourite_event -> {
                navigate(R.id.action_eventDetailFragment2_to_postLoginFragment)
            }
        }
    }
    private fun locationPermission() {
        val quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = false
        )
        activity.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            options = quickPermissionsOption
        ) {
            locationHelper.locationSetUp(
                object : LocationHelper.LocationLatLng {
                    @SuppressLint("SetTextI18n")
                    override fun getCurrentLocation(location: Location) {
                        loc = location
                        Timber.e("Current Location ${location.latitude}")
                        if (eventObj.latitude!!.isNotEmpty() && eventObj.longitude!!.isNotEmpty())
                            binding.root.tv_km.text = locationHelper.distance(loc.latitude,
                                loc.longitude,
                                eventObj.latitude!!.toDouble(),
                                eventObj.longitude!!.toDouble())
                                .toString() + resources.getString(R.string.away)
                    }
                },
                object : LocationHelper.LocationCallBack {
                    override fun getLocationResultCallback(locationResult: LocationResult?) {
                        Timber.e("LocationResult ${locationResult!!.lastLocation.latitude}")
                    }

                }, activity)
        }
    }
    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }

}