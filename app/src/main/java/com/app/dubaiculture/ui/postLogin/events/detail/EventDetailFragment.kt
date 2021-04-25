package com.app.dubaiculture.ui.postLogin.events.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItems
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.app.dubaiculture.databinding.EventItemsBinding
import com.app.dubaiculture.databinding.FragmentEventDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils.getFacebookPage
import com.app.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils.openUrl
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.events.detail.adapter.ScheduleExpandAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import com.app.dubaiculture.utils.Constants.NavBundles.EVENT_OBJECT
import com.app.dubaiculture.utils.GpsStatus
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.getTimeSpan
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.event_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.event_detail_schedule_layout.view.*
import kotlinx.android.synthetic.main.fragment_event_detail.view.*
import kotlinx.android.synthetic.main.fragment_event_detail.view.back
import kotlinx.android.synthetic.main.fragment_event_detail.view.favourite
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.view.*
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.view.category
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.view.title
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
    var isDetailFavouriteFlag = false
    var emailContact : String? = null
    var numberContact : String? = null

    private lateinit var marker:Marker

    private val getObserver = Observer<GpsStatus> {
        it?.let {
            updateGpsCheckUi(it)
        }
    }

    private fun subscribeToGpsListener() = eventViewModel.gpsStatusLiveData
        .observe(viewLifecycleOwner, getObserver)

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

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationIsEmpty(locationResult.lastLocation)
        }
    }

    private var eventObj = Events()
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
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
        subscribeToGpsListener()
        enableRegistration()
        if (eventObj.isFavourite) {
            binding.favourite.background = getDrawableFromId(R.drawable.heart_icon_fav)
            binding.root.favourite_event.background =
                getDrawableFromId(R.drawable.heart_icon_fav)
        }

        binding.root.btn_register_now.setOnClickListener {
            navigate(R.id.action_eventDetailFragment2_to_registerNowFragment)
        }
        binding.root.tv_direction.setOnClickListener {

        }
        binding.root.ll_callus.setOnClickListener {
            openDiallerBox( numberContact)

        }
        binding.root.ll_email_us.setOnClickListener {
            openEmailbox(email = emailContact.toString())
        }
        binding.root.imgFb.setOnClickListener {
            getFacebookPage(eventObj.socialLink?.get(0)?.facebookPageLink!!, activity)
        }
        binding.root.imgTwitter.setOnClickListener {
            openUrl(eventObj.socialLink?.get(0)?.twitterPageLink, activity)
        }
        binding.root.imgInsta.setOnClickListener {
            openUrl(eventObj.socialLink?.get(0)?.instagramPageLink, activity)
        }
        binding.root.imgUtube.setOnClickListener {
            openUrl(eventObj.socialLink?.get(0)?.youtubePageLink, activity)
        }
        binding.root.imgLinkedin.setOnClickListener {
            openUrl(eventObj.socialLink?.get(0)?.linkedInPageLink, activity)
        }
        binding.root.favourite_event.setOnClickListener {
            isDetailFavouriteFlag = true
            eventObj.let { event ->
                favouriteClick(
                    it.favourite_event,
                    event.isFavourite,
                    R.id.action_eventDetailFragment2_to_postLoginFragment,
                    event.id!!,
                    eventViewModel,
                    2
                )
            }
        }
        binding.favourite.setOnClickListener {
            isDetailFavouriteFlag = true
            eventObj.let { event ->
                favouriteClick(
                    it.favourite,
                    event.isFavourite,
                    R.id.action_eventDetailFragment2_to_postLoginFragment,
                    event.id!!,
                    eventViewModel,
                    2
                )
            }
        }

        binding.root.rbEventInfo.setOnClickListener {
            binding.root.ll_even_info.visibility = View.VISIBLE
            binding.root.ll_schedule.visibility = View.GONE
        }
        binding.root.rbSchedule.setOnClickListener {
            binding.root.ll_even_info.visibility = View.GONE
            binding.root.ll_schedule.visibility = View.VISIBLE
            verticalLayoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
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
        eventViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        if (isDetailFavouriteFlag) {
                            binding.favourite.background =
                                getDrawableFromId(R.drawable.heart_icon_fav)
                            binding.root.favourite_event.background =
                                getDrawableFromId(R.drawable.heart_icon_fav)
                            isDetailFavouriteFlag = false
                        }
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_fav)

                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_home_black)
                        if (isDetailFavouriteFlag) {
                            binding.favourite.background =
                                getDrawableFromId(R.drawable.heart_icon_home_black)
                            binding.root.favourite_event.background =
                                getDrawableFromId(R.drawable.heart_icon_home_black)
                            isDetailFavouriteFlag = false

                        }

                    }
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
                tv_event_date.text =
                    "${eventObj.fromDate} - ${eventObj.toDate} ${eventObj.fromMonthYear}"
                glide.load(com.app.dubaiculture.BuildConfig.BASE_URL + eventObj.image)
                    .into(imageView)
            }
        }
        binding.root.btn_reg.setOnClickListener(this)
        binding.root.back.setOnClickListener(this)
        binding.root.back_event.setOnClickListener(this)
        binding.root.img_share_event.setOnClickListener(this)
        binding.root.bookingCalender_event.setOnClickListener(this)
        binding.root.favourite_event.setOnClickListener(this)
        binding.root.favourite.setOnClickListener(this)
        binding.root.img_event_speaker.setOnClickListener(this)
        binding.root.speaker_schedule.setOnClickListener(this)
        backArrowRTL(binding.root.back_event)
        backArrowRTL(binding.root.back)
        bgRTL(binding.root.bg_border_event)
        bgEventtRTL(binding.root.img)



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

            map?.addMarker(
                MarkerOptions()
                    .position(trafficDigitalLatLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))
            )!!
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
                if (binding.root.tv_desc_readmore_event.text.isNotEmpty()) {
                    textToSpeechEngine.speak(
                        binding.root.tv_desc_readmore_event.text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1"
                    )
                }
            }
            R.id.btn_reg -> {
                navigate(R.id.action_eventDetailFragment2_to_registerNowFragment)
            }
//            R.id.favourite -> {
//
//                navigate(R.id.action_eventDetailFragment2_to_postLoginFragment)
//            }
            R.id.back -> {
                back()
            }
            R.id.back_event -> {
                back()
            }
            R.id.img_share_event -> {
//                eventViewModel.showToast("Share")
            }
            R.id.bookingCalender_event -> {
//                eventViewModel.showToast("Calender")
            }
            R.id.favourite_event -> {
                navigate(R.id.action_eventDetailFragment2_to_postLoginFragment)
            }
            R.id.speaker_schedule -> {
                if (binding.root.tv_schedule_title.text.isNotEmpty())
                    textToSpeechEngine.speak(
                        binding.root.tv_schedule_title.text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1"
                    )
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
                            binding.root.tv_km.text = locationHelper.distance(
                                loc.latitude,
                                loc.longitude,
                                eventObj.latitude!!.toDouble(),
                                eventObj.longitude!!.toDouble()
                            )
                                .toString() + resources.getString(R.string.away)
                    }
                }, activity, locationCallback
            )
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
                    emailContact = it.value.emailContact
                    numberContact = it.value.numberContact
                    it.value.relatedEvents!!.forEach {
                        moreEvents.add(it)
                    }
                    if (!it.value.desc.isNullOrEmpty()) {
                        binding.root.tv_desc_readmore_event.text = it.value.desc
                    }
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
                                val eventObj = moreEvents[position]
                                val bundle = Bundle()
                                bundle.putParcelable(
                                    EVENT_OBJECT,
                                    eventObj
                                )
                                navigate(
                                    R.id.action_eventDetailFragment2_to_eventDetailFragment2,
                                    bundle
                                )
                            }
                        }, event = it, resLayout = R.layout.event_items, activity))
                    }
                }
                is Result.Failure -> {
                    eventViewModel.showErrorDialog(message = INTERNET_CONNECTION_ERROR)

                }
            }
        }

    }

    private fun updateGpsCheckUi(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                locationPermission()


            }
            is GpsStatus.Disabled -> {

                eventViewModel.showErrorDialog(message = "Please enable Location")
            }
        }
    }

    private fun locationIsEmpty(location: Location) {
        if (eventObj.latitude!!.isNotEmpty() && eventObj.longitude!!.isNotEmpty()) {
            binding.root.tv_km.text =
                locationHelper.distance(
                    location.latitude,
                    location.longitude,
                    eventObj.latitude!!.toDouble(),
                    eventObj.longitude!!.toDouble()
                )
                    .toString() + resources.getString(R.string.away)
        }
    }

    private fun enableRegistration() {
        if (!getTimeSpan(eventObj.dateFrom, eventObj.dateTo)) {
            binding.root.btn_reg.isEnabled = false
            binding.root.btn_register_now.isEnabled = false
        }
    }
}





