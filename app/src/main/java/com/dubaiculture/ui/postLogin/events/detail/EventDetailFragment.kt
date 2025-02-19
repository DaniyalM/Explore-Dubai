package com.dubaiculture.ui.postLogin.events.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.CalendarContract
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItems
import com.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.dubaiculture.databinding.EventDetailInnerLayoutBinding
import com.dubaiculture.databinding.EventDetailScheduleLayoutBinding
import com.dubaiculture.databinding.FragmentEventDetailBinding
import com.dubaiculture.databinding.ToolbarLayoutEventDetailBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.attractions.detail.viewmodels.EventDetailViewModel
import com.dubaiculture.ui.postLogin.attractions.utils.SocialLink
import com.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils
import com.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils.getFacebookPage
import com.dubaiculture.ui.postLogin.events.`interface`.EventClickListner
import com.dubaiculture.ui.postLogin.events.adapters.EventAdapter
import com.dubaiculture.ui.postLogin.events.detail.adapter.ScheduleExpandAdapter
import com.dubaiculture.utils.*
import com.dubaiculture.utils.AppConfigUtils.shareLink
import com.dubaiculture.utils.Constants.GoogleMap.DESTINATION
import com.dubaiculture.utils.Constants.GoogleMap.LINK_URI
import com.dubaiculture.utils.Constants.GoogleMap.PACKAGE_NAME_GOOGLE_MAP
import com.dubaiculture.utils.Constants.NavBundles.EVENT_OBJECT
import com.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class EventDetailFragment : BaseFragment<FragmentEventDetailBinding>(),
    OnMapReadyCallback, View.OnClickListener, AppBarLayout.OnOffsetChangedListener {
    private val eventViewModel: EventDetailViewModel by viewModels()
    private lateinit var verticalLayoutManager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    val parentItemList = ArrayList<EventScheduleItems>()
    val getTimeSlotList = ArrayList<EventScheduleItemsSlots>()
    var slotTime = ArrayList<EventScheduleItemsSlots>()
    val moreEvents = ArrayList<Events>()
    val childItemHolder: ArrayList<ArrayList<EventScheduleItemsSlots>> = ArrayList()
    var isDetailFavouriteFlag = false
    var emailContact: String? = null
    var numberContact: String? = null
    var urlshare: String? = null
    var map: GoogleMap? = null


    var isRegisterd = false

    lateinit var eventDetailInnerLayout: EventDetailInnerLayoutBinding
    lateinit var eventDetailScheduleLayoutBinding: EventDetailScheduleLayoutBinding
    lateinit var toolbarLayoutEventDetailBinding: ToolbarLayoutEventDetailBinding

    //    var eventListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
//    private lateinit var eventListScreenAdapter: EventListScreenAdapter
    private lateinit var eventListScreenAdapter: EventAdapter


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
    private var mapView: MapView? = null


    var loc = Location("dummyprovider")

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationIsEmpty(locationResult.lastLocation)
        }
    }

    private var eventObj: Events? = Events(
        location = "",
        longitude = "67.08119661055807",
        latitude = "24.83250180519734",
        registrationDate = "",
        dateFrom = "",
        dateTo = "",
    )
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
            eventObj = it.getParcelable(EVENT_OBJECT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapSetUp(savedInstanceState)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        eventDetailInnerLayout = binding.eventDetailInnerLayout
        toolbarLayoutEventDetailBinding = binding.toolbarLayoutEventDetail
        rvSetUp()
        subscribeUiEvents(eventViewModel)
        callingObservables()

        arrowRTL(eventDetailInnerLayout.imgEventSpeaker)
        subscribeToGpsListener()






        eventDetailInnerLayout.imgFb.setOnClickListener {
            getFacebookPage(
                eventObj?.socialLink?.get(0)!!.facebookPageLink,
                activity,
            )
//            SocialNetworkUtils.openUrl(
//                eventObj?.socialLink?.get(0)!!.facebookPageLink,
//                activity,
//                isFacebook = true,
//                fragment = this
//            )
        }
        eventDetailInnerLayout.imgTwitter.setOnClickListener {
            SocialNetworkUtils.openUrl(
                eventObj?.socialLink?.get(0)!!.twitterPageLink,
                activity,
                isTwitter = true,
                fragment = this,
                title = SocialLink.TWITTER.title
            )
        }
        eventDetailInnerLayout.imgInsta.setOnClickListener {
            SocialNetworkUtils.openUrl(
                eventObj?.socialLink?.get(0)!!.instagramPageLink,
                activity,
                isInstagram = true,
                fragment = this,
                title = SocialLink.INSTAGRAM.title
            )
        }
        eventDetailInnerLayout.imgUtube.setOnClickListener {
            SocialNetworkUtils.openUrl(
                eventObj?.socialLink?.get(0)!!.youtubePageLink,
                activity,
                isYoutube = true,
                fragment = this,
                title = SocialLink.YOUTUBE.title
            )
        }
        eventDetailInnerLayout.imgLinkedin.setOnClickListener {
            SocialNetworkUtils.openUrl(
                eventObj?.socialLink?.get(0)!!.linkedInPageLink,
                activity,
                isLinkedIn = true,
                fragment = this,
                title = SocialLink.LINKEDIN.title
            )
        }

        eventDetailInnerLayout.rbEventInfo.setOnClickListener {
            eventDetailInnerLayout.llEvenInfo.visibility = View.VISIBLE
            eventDetailInnerLayout.llSchedule.visibility = View.GONE
        }
        eventDetailInnerLayout.rbSchedule.setOnClickListener {
            eventDetailInnerLayout.llEvenInfo.visibility = View.GONE
            eventDetailInnerLayout.llSchedule.visibility = View.VISIBLE
            verticalLayoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            myAdapter = ScheduleExpandAdapter(requireActivity(), parentItemList, childItemHolder)
            eventDetailInnerLayout.eventDetailScheduleLayout.rvSchedule.apply {
                setHasFixedSize(true)
                layoutManager = verticalLayoutManager
                adapter = myAdapter
            }
        }
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            binding.swipeRefreshLayout.isRefreshing = false
//            callingObservables()
//        }

    }


    private fun callingObservables() {
//        eventViewModel.getEventDetailsToScreen(eventObj?.id!!, getCurrentLanguage().language)
        eventViewModel.eventDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    eventObj = it.value

                    if (it.value.numberContact.isNullOrEmpty()) {
                        eventDetailInnerLayout.llCallus.alpha = 0.2f
                        eventDetailInnerLayout.llCallus.isClickable = false
                    } else {
                        eventDetailInnerLayout.llCallus.setOnClickListener {
                            openDiallerBox(eventObj?.numberContact)
                        }
                    }
                    if (it.value.emailContact.isNullOrEmpty()) {
                        eventDetailInnerLayout.llEmailUs.alpha = 0.2f
                        eventDetailInnerLayout.llEmailUs.isClickable = false
                    } else {
                        eventDetailInnerLayout.llEmailUs.setOnClickListener {
                            openEmailbox(email = eventObj?.emailContact.toString())
                        }
                    }
                    urlshare = "${it.value.url}?q=${it.value.id}"
                    binding.toolbarLayoutEventDetail.favouriteEvent.setOnClickListener {
                        isDetailFavouriteFlag = true
                        eventObj?.let { event ->
                            favouriteClick(
                                binding.toolbarLayoutEventDetail.favouriteEvent,
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
                        eventObj?.let { event ->
                            favouriteClick(
                                binding.favourite,
                                event.isFavourite,
                                R.id.action_eventDetailFragment2_to_postLoginFragment,
                                event.id!!,
                                eventViewModel,
                                2
                            )
                        }
                    }
                    if (urlshare != null && !urlshare!!.isEmpty()) {
                        toolbarLayoutEventDetailBinding.imgShareEvent.setOnClickListener {
                            shareLink(
                                urlshare
                                    ?: "https://dc.qa.greenlightlabs.tech/en/events/Certified-Cultural-Guide",
                                activity,
                                title = eventObj!!.title!! + " " + eventObj!!.fromDate + "-" + eventObj!!.fromMonthYear,
                                detail = ""
                            )
                        }
                        binding.share.setOnClickListener {
                            shareLink(
                                urlshare
                                    ?: "https://dc.qa.greenlightlabs.tech/en/events/Certified-Cultural-Guide",
                                activity,
                                title = eventObj!!.title!!,
                                detail = ""
                            )

                        }
                    } else {
                        toolbarLayoutEventDetailBinding.imgShareEvent.hide()
                        binding.share.hide()
                    }
                    locationPermission(it.value)
                    it.value.apply {
                        enableRegistration(registrationDate, it.value)
                        if (isFavourite) {
                            binding.favourite.background =
                                getDrawableFromId(R.drawable.heart_icon_fav)
                            binding.toolbarLayoutEventDetail.favouriteEvent.background =
                                getDrawableFromId(R.drawable.heart_icon_fav)
                        }

                    }

                    uiActions()
                    try {
                        if (it.value.latitude.isNotEmpty()) {
                            val trafficDigitalLatLng =
                                LatLng(
                                    (it.value.latitude.toDouble()),
                                    it.value.longitude.toDouble()
                                )

                            if (map != null) {
                                map?.apply {
                                    addMarker(
                                        MarkerOptions()
                                            .position(trafficDigitalLatLng)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))
                                    )!!.title = it.value.title
                                    animateCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                            trafficDigitalLatLng, 14.0f
                                        )
                                    )
                                    cameraPosition.target
                                }
                            }


                        }
                    } catch (e: NumberFormatException) {
                        print(e.stackTrace)
                    }
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
                            binding.favourite1.hide()
                            toolbarLayoutEventDetailBinding.favouriteEvent1.hide()
                            binding.toolbarLayoutEventDetail.favouriteEvent.background =
                                getDrawableFromId(R.drawable.heart_icon_fav)
                            isDetailFavouriteFlag = false
                        }
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_fav)

                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_home_black)
                        if (isDetailFavouriteFlag) {
                            binding.favourite.background =
                                getDrawableFromId(R.drawable.heart_icon_home_black)
                            binding.favourite1.show()
                            toolbarLayoutEventDetailBinding.favouriteEvent1.show()
                            toolbarLayoutEventDetailBinding.favouriteEvent.background =
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


        binding.toolbarLayoutEventDetail.bookingCalenderEvent.show()
        binding.bookingCalender.show()

        binding.toolbarLayoutEventDetail.backEvent.setOnClickListener(this)
//        binding.toolbarLayoutEventDetail.imgShareEvent.setOnClickListener(this)
        binding.toolbarLayoutEventDetail.bookingCalenderEvent.setOnClickListener(this)
        binding.bookingCalender.setOnClickListener(this)
//        binding.toolbarLayoutEventDetail.favouriteEvent.setOnClickListener(this)

        binding.eventDetailInnerLayout.imgEventSpeaker.setOnClickListener(this)
        binding.eventDetailInnerLayout.eventDetailScheduleLayout.speakerSchedule.setOnClickListener(
            this
        )
        binding.eventDetailInnerLayout.tvDirectionEvent.setOnClickListener(this)
        backArrowRTL(binding.back)
        bgRTL(binding.toolbarLayoutEventDetail.bgBorderEvent)
        bgEventtRTL(binding.eventDetailInnerLayout.img)

        binding.apply {
            toolbarLayoutEventDetail.apply {
                backArrowRTL(backEvent)

                title.text = eventObj?.title
                category.text = eventObj?.category
                tvEventDate.text =
                    "${eventObj?.fromDate} - ${eventObj?.toDate} ${eventObj?.fromMonthYear}"
            }
            eventDetailInnerLayout.apply {
                tvTitle.text = eventObj?.title
                tvLocation.text = eventObj?.locationTitle
                tvEventDaysDate.text =
                    "${eventObj?.toDate}- ${eventObj?.fromDate} ${eventObj?.fromMonthYear}  |  ${eventObj?.fromDay} - ${eventObj?.toDay}"
                tvTimes.text = "${eventObj?.fromTime} - ${eventObj?.toTime}"
                tvCategory.text = eventObj?.category

                glide.load(BuildConfig.BASE_URL + eventObj?.image)
                    .into(toolbarLayoutEventDetail.imageView)
            }
        }
        binding.back.setOnClickListener(this)

    }

    private fun rvSetUp() {
        eventDetailInnerLayout.rvEventUpComing.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            eventListScreenAdapter = EventAdapter(eventClickListner = object : EventClickListner {
                override fun checkFavListener(
                    checkbox: CheckBox,
                    pos: Int,
                    isFav: Boolean,
                    itemId: String
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

                override fun rowClickHandler(events: Events) {

                    navigateByDirections(
                        EventDetailFragmentDirections.actionEventDetailFragment2ToEventDetailFragment2(
                            events.id!!
                        )
                    )
                }
            }
            )


            adapter = eventListScreenAdapter
        }
        initiateExpander()

    }

    private fun mapSetUp(savedInstanceState: Bundle?) {

        if (mapView == null) {
            mapView = binding.root.findViewById(R.id.map)
            mapView?.let {
                it.getMapAsync(this)
                it.onCreate(savedInstanceState)

            }
        }

    }

    override fun onMapReady(map: GoogleMap?) {

        map?.let {
            this.map = it
            it.uiSettings.setAllGesturesEnabled(false)

        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvDirectionEvent -> {
                if (!eventObj?.latitude.isNullOrEmpty()) {
                    val uri =
                        LINK_URI + loc.latitude.toString() + "," + loc.longitude.toString() + DESTINATION + eventObj?.latitude.toString() + "," + eventObj?.longitude
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage(PACKAGE_NAME_GOOGLE_MAP)
                    try {
                        startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {
                        eventViewModel.showToast("Please install a Google map application")
                    }
                }
            }
            R.id.img_event_speaker -> {
                if (binding.eventDetailInnerLayout.tvDescReadmoreEvent.text.isNotEmpty()) {
                    if (textToSpeechEngine.isSpeaking) {
                        textToSpeechEngine.stop()
                    } else {
                        textToSpeechEngine.speak(
                            "${eventObj?.title} ${eventObj?.desc}",
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "tts1"
                        )
                    }

                }
            }
//            R.id.btn_reg -> {
//
//
//
//            }
//            R.id.favourite -> {00000000
//
//                navigate(R.id.action_eventDetailFragment2_to_postLoginFragment)
//            }
            R.id.back -> {
                back()
            }
            R.id.back_event -> {
                back()
            }
//            R.id.img_share_event -> {
////                eventViewModel.showToast("Share")
//            }
            R.id.bookingCalender_event -> {
                if (Build.VERSION.SDK_INT >= 14) {
                    val intent: Intent = Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EventDays.STARTDAY, eventObj?.fromDate)
                        .putExtra(CalendarContract.EventDays.ENDDAY, eventObj?.toDate)
                        .putExtra(CalendarContract.Events.TITLE, eventObj?.title)
                        .putExtra(CalendarContract.Events.DESCRIPTION, eventObj?.desc)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, eventObj?.location)
                        .putExtra(Intent.EXTRA_EMAIL, eventObj?.emailContact)
                    startActivity(intent)
                } else {
                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.type = "vnd.android.cursor.item/event"
                    intent.putExtra("startDay", eventObj?.fromDate)
                    intent.putExtra("allDay", true)
                    intent.putExtra("rrule", "FREQ=YEARLY")
                    intent.putExtra("endDay", eventObj?.toDate)
                    intent.putExtra("title", eventObj?.title)
                    startActivity(intent)
                }
            }
            R.id.bookingCalender -> {
                if (Build.VERSION.SDK_INT >= 14) {
                    val intent: Intent = Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EventDays.STARTDAY, eventObj?.fromDate)
                        .putExtra(CalendarContract.EventDays.ENDDAY, eventObj?.toDate)
                        .putExtra(CalendarContract.Events.TITLE, eventObj?.title)
                        .putExtra(CalendarContract.Events.DESCRIPTION, eventObj?.desc)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, eventObj?.location)
                        .putExtra(Intent.EXTRA_EMAIL, eventObj?.emailContact)
                    startActivity(intent)
                } else {
                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.type = "vnd.android.cursor.item/event"
                    intent.putExtra("startDay", eventObj?.fromDate)
                    intent.putExtra("allDay", true)
                    intent.putExtra("rrule", "FREQ=YEARLY")
                    intent.putExtra("endDay", eventObj?.toDate)
                    intent.putExtra("title", eventObj?.title)
                    startActivity(intent)
                }
            }
//            R.id.favourite_event -> {
//                navigate(R.id.action_eventDetailFragment2_to_postLoginFragment)
//            }
            R.id.speaker_schedule -> {
                if (binding.eventDetailInnerLayout.eventDetailScheduleLayout.tvScheduleTitle.text.isNotEmpty())
                    textToSpeechEngine.speak(
                        binding.eventDetailInnerLayout.eventDetailScheduleLayout.tvScheduleTitle.text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1"
                    )
            }
        }
    }

    private fun locationPermission(events: Events) {
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
                        if (events.latitude.isNotEmpty() && events.longitude.isNotEmpty())
                            binding.eventDetailInnerLayout.tvKm.text = locationHelper.distance(
                                loc.latitude,
                                loc.longitude,
                                events.latitude.toDouble(),
                                events.longitude.toDouble()
                            ).toString() + " " + resources.getString(R.string.away)
                    }
                },
                locationCallback
            )
        }
    }


    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
//        mapView?.onDestroy()
    }


    private fun initiateExpander() {
        eventViewModel.eventDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    isRegisterd = it.value.isRegistered

//                    enableRegistration(it.value.registrationDate,it.value)
                    slotTime.clear()
                    parentItemList.clear()

                    it.value.relatedEvents!!.forEach {
                        moreEvents.add(it)
                    }
                    eventListScreenAdapter.submitList(it.value.relatedEvents)
                    if (!it.value.desc.isNullOrEmpty()) {
                        binding.eventDetailInnerLayout.tvDescReadmoreEvent.text = it.value.desc
                    }
                    if (it.value.eventSchedule.isEmpty()) {
                        binding.eventDetailInnerLayout.eventDetailScheduleLayout.noDataPlaceHolder?.show()
                        binding.eventDetailInnerLayout.eventDetailScheduleLayout.rvSchedule.hide()
                        binding.eventDetailInnerLayout.eventDetailScheduleLayout.tvScheduleTitle.hide()
                        binding.eventDetailInnerLayout.eventDetailScheduleLayout.speakerSchedule.hide()
                    } else {
                        binding.eventDetailInnerLayout.eventDetailScheduleLayout.noDataPlaceHolder!!.hide()
                    }

                    it.value.eventSchedule!!.map {
                        binding.eventDetailInnerLayout.eventDetailScheduleLayout.tvScheduleTitle.text =
                            it.description
                        it.eventScheduleItems.forEach {
                            parentItemList.add(it)
                        }

                        parentItemList.forEach {
                            childItemHolder.add(it.eventScheduleItemsSlots as ArrayList<EventScheduleItemsSlots>)
                        }

                    }
                    slotTime = getScheduleTimeSlot()


                }
                is Result.Failure -> {
                    handleApiError(it, eventViewModel)

                }
            }
        }

    }

    private fun updateGpsCheckUi(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                eventObj?.apply {
                    locationPermission(this)

                }
            }
            is GpsStatus.Disabled -> {
                eventViewModel.showToast(message = "Please enable location !")
            }
        }
    }

    private fun locationIsEmpty(location: Location) {
        if (eventObj?.latitude?.isNotEmpty()!! && eventObj?.longitude?.isNotEmpty()!!) {
            binding.eventDetailInnerLayout.tvKm.text =
                locationHelper.distance(
                    location.latitude,
                    location.longitude,
                    eventObj?.latitude?.toDouble()!!,
                    eventObj?.longitude?.toDouble()!!
                )
                    .toString() + resources.getString(R.string.away)
        }
    }

    private fun registrationAction() {
        if (application.auth.isGuest) {
            navigateByDirections(EventDetailFragmentDirections.actionEventDetailFragment2ToPostLoginFragment())
        } else {
            val bundle = Bundle()
            bundle.putParcelableArrayList(
                Constants.NavBundles.SCHEDULE_ITEM_SLOT, slotTime as ArrayList<out Parcelable>
            )
            bundle.putString(Constants.NavBundles.EVENT_ID, eventObj?.id)
//                navigate(R.id.action_eventDetailFragment2_to_registerNowFragment,bundle)
            findNavController().navigate(
                R.id.action_eventDetailFragment2_to_registerNowFragment,
                bundle
            )
        }
    }

    private fun enableRegistration(registrationDate: String, events: Events? = null) {
        if (getTimeSpan(registrationDate)) {
            binding.toolbarLayoutEventDetail.btnReg.text =
                resources.getString(R.string.submit_survey)
            binding.eventDetailInnerLayout.btnRegisterNow.text =
                resources.getString(R.string.submit_survey)
            binding.eventDetailInnerLayout.btnRegisterNow.alpha = 1f
            binding.toolbarLayoutEventDetail.btnReg.alpha = 1f
            events?.let {
                //Submit Survey Will Work Here
                if (!it.IsSurveyed) {
                    binding.toolbarLayoutEventDetail.btnReg.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString(
                            "event_id",
                            events.id ?: "0E49F5666F904C92B1BC41A13FD50B53"
                        )
                        navigate(R.id.action_eventDetailFragment2_to_surveyFragment2, bundle)
                    }
                    binding.eventDetailInnerLayout.btnRegisterNow.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString(
                            "event_id",
                            events.id ?: "0E49F5666F904C92B1BC41A13FD50B53"
                        )
                        navigate(R.id.action_eventDetailFragment2_to_surveyFragment2, bundle)
                    }
                } else {
                    binding.toolbarLayoutEventDetail.btnReg.text =
                        resources.getString(R.string.survey_submitted)
                    binding.eventDetailInnerLayout.btnRegisterNow.text =
                        resources.getString(R.string.survey_submitted)

                    binding.toolbarLayoutEventDetail.btnReg.background = ContextCompat.getDrawable(
                        activity,
                        R.drawable.bg_btn_filled_disabled
                    )
                    binding.eventDetailInnerLayout.btnRegisterNow.background =
                        ContextCompat.getDrawable(
                            activity,
                            R.drawable.bg_btn_filled_disabled
                        )
                    binding.toolbarLayoutEventDetail.btnReg.isClickable = false
                    binding.eventDetailInnerLayout.btnRegisterNow.isClickable = false

                }
            }
            if (!isRegisterd) {
                binding.toolbarLayoutEventDetail.btnReg.hide()
                binding.eventDetailInnerLayout.btnRegisterNow.hide()
            }


        } else {
            if (isRegisterd) {
                binding.toolbarLayoutEventDetail.btnReg.isClickable = false
                binding.eventDetailInnerLayout.btnRegisterNow.isClickable = false
                binding.toolbarLayoutEventDetail.btnReg.alpha = 0.4f
                binding.eventDetailInnerLayout.btnRegisterNow.alpha = 0.4f
            } else {
                binding.toolbarLayoutEventDetail.btnReg.isClickable = true
                binding.eventDetailInnerLayout.btnRegisterNow.isClickable = true
                binding.toolbarLayoutEventDetail.btnReg.alpha = 1f
                binding.eventDetailInnerLayout.btnRegisterNow.alpha = 1f

                binding.toolbarLayoutEventDetail.btnReg.setOnClickListener {
                    registrationAction()
                }

                eventDetailInnerLayout.btnRegisterNow.setOnClickListener {
                    registrationAction()
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()

        binding.appbarEventnDetail
            .addOnOffsetChangedListener(this)

    }

    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
        mapView?.onPause()

        binding.appbarEventnDetail.removeOnOffsetChangedListener(this)

    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == -binding.toolbarLayoutEventDetail.collapsingToolbarEventDetail.height + binding.toolbarLayoutEventDetail.toolbarEventDetail.height) {
            binding.defaultCloseToolbar.visibility = View.VISIBLE
            binding.imageView4.visibility = View.VISIBLE
//            binding.swipeRefreshLayout.isEnabled = false
        } else {
            binding.defaultCloseToolbar.visibility = View.GONE
            binding.imageView4.visibility = View.GONE
//            binding.swipeRefreshLayout.isEnabled = true
        }
    }

    private fun getScheduleTimeSlot(): ArrayList<EventScheduleItemsSlots> {
        parentItemList.map {
            it.eventScheduleItemsSlots?.forEach {
                getTimeSlotList.add(
                    EventScheduleItemsSlots(
                        timeFrom = it.timeFrom,
                        timeTo = it.timeTo,
                        slotId = it.slotId,
                        summary = it.summary
                    )
                )
            }
        }
        return getTimeSlotList
    }
}





