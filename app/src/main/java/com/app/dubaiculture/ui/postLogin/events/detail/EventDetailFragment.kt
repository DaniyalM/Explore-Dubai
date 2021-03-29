package com.app.dubaiculture.ui.postLogin.events.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItems
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.app.dubaiculture.databinding.EventItemsBinding
import com.app.dubaiculture.databinding.FragmentEventDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.events.detail.adapter.ScheduleExpandAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import com.app.dubaiculture.utils.Constants.NavBundles.EVENT_OBJECT
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.handleApiError
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
import kotlinx.android.synthetic.main.event_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.event_detail_schedule_layout.view.*
import kotlinx.android.synthetic.main.event_search_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_event_detail.view.*
import kotlinx.android.synthetic.main.fragment_event_detail.view.back
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.view.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class EventDetailFragment : BaseFragment<FragmentEventDetailBinding>(),
    OnMapReadyCallback, View.OnClickListener {
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var verticalLayoutManager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    val parentItemList = ArrayList<EventScheduleItems>()
    val moreEvents = ArrayList<Events>()
    val childItemHolder: ArrayList<ArrayList<EventScheduleItemsSlots>> = ArrayList()

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
    ): FragmentEventDetailBinding {
        return FragmentEventDetailBinding.inflate(inflater, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            eventObj = it.getParcelable(EVENT_OBJECT)!!
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationPermission()
        subscribeUiEvents(eventViewModel)
        callingObservables()

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
            verticalLayoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,
                false)
            myAdapter = ScheduleExpandAdapter(requireActivity(), parentItemList, childItemHolder)
            binding.root.rvSchedule.apply {
                setHasFixedSize(true)
                layoutManager = verticalLayoutManager
                adapter = myAdapter
            }
        }
    }

    private fun callingObservables() {
        eventViewModel.getEventDetailsToScreen(eventObj.id!!, getCurrentLanguage().language)
        eventViewModel.eventDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    eventObj = it.value
                }

                is Result.Failure -> handleApiError(it, eventViewModel)
            }

        }

    }


    private fun uiActions() {
        binding.apply {
            root.apply {
                title.text = eventObj.title
                tv_title.text = eventObj.title
                tv_location.text = eventObj.locationTitle
                tv_event_days_date.text =
                    "${eventObj.toDate}- ${eventObj.fromDate} ${eventObj.fromMonthYear}  |  ${eventObj.fromDay} - ${eventObj.toDay}"
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
        binding.root.speaker_schedule.setOnClickListener(this)
        backArrowRTL(binding.root.back_event)
        backArrowRTL(binding.root.back)


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
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
        }
        initiateExpander()

    }

    private fun mapSetUp() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap?) {
        if (eventObj.latitude!!.isNotEmpty()) {
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
                if (binding.root.tv_desc_readmore.text.isNotEmpty()) {
                    textToSpeechEngine.speak(binding.root.tv_desc_readmore.text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1")
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
                eventViewModel.showToast("Swipe up")
            }
            R.id.img_share_event -> {
                eventViewModel.showToast("Share")
            }
            R.id.bookingCalender_event -> {
                eventViewModel.showToast("Calender")
            }
            R.id.favourite_event -> {
                navigate(R.id.action_eventDetailFragment2_to_postLoginFragment)
            }
            R.id.speaker_schedule -> {
                if (binding.root.tv_schedule_title.text.isNotEmpty())
                    textToSpeechEngine.speak(binding.root.tv_schedule_title.text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1")
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


    private fun initiateExpander() {
        eventViewModel.eventDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.value.relatedEvents!!.forEach {
                        moreEvents.add(it)
                    }
                    binding.root.tv_desc_readmore.text = it.value.desc
                    it.value.eventSchedule!!.map {
                        binding.root.tv_schedule_title.text = it.description
                        it.eventScheduleItems.forEach {
                            parentItemList.add(it)
                        }
                        parentItemList.forEach {
                            childItemHolder.add(it.eventScheduleItemsSlots as ArrayList<EventScheduleItemsSlots>)
                        }
                    }

                    moreEvents.map {
                        groupAdapter.add(EventListItem<EventItemsBinding>(object :
                            FavouriteChecker {
                            override fun checkFavListener(
                                checkbox: CheckBox,
                                pos: Int,
                                isFav: Boolean,
                                itemId: String,
                            ) {
                                favouriteClick(
                                    checkbox,
                                    isFav,
                                    type = 2,
                                    itemId = itemId,
                                    baseViewModel = eventViewModel,
                                    nav = R.id.action_eventDetailFragment2_to_postLoginFragment
                                )
                            }
                        }, object : RowClickListener {
                            override fun rowClickListener(position: Int) {
//                                val eventObj = moreEvents[position]
//                                val bundle = Bundle()
//                                bundle.putParcelable(EVENT_OBJECT,
//                                    eventObj)
//                                navigate(R.id.action_eventsFragment_to_eventDetailFragment2,
//                                    bundle)


                            }
                        }, event = it, resLayout = R.layout.event_items))
                    }
                }
                is Result.Failure -> {
                    eventViewModel.showErrorDialog(message = INTERNET_CONNECTION_ERROR)

                }
            }
        }

    }
}