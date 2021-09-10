package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionDetailInnerLayoutBinding
import com.app.dubaiculture.databinding.AttractionDetailUpComingItemsBinding
import com.app.dubaiculture.databinding.FragmentAttractionDetailBinding
import com.app.dubaiculture.databinding.ToolbarLayoutDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.AttractionDetailViewModel
import com.app.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils.openUrl
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_GALLERY_LIST
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_ID
import com.app.dubaiculture.utils.Constants.NavBundles.THREESIXTY_GALLERY_LIST
import com.app.dubaiculture.utils.GpsStatus
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.RequestManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import com.estimote.coresdk.common.requirements.SystemRequirementsHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.shape.CornerFamily
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>(),
    OnMapReadyCallback, View.OnClickListener, AppBarLayout.OnOffsetChangedListener {
    private var url: String? = null
    var emailContact: String? = null
    var numberContact: String? = null
    lateinit var detailInnerLayout: AttractionDetailInnerLayoutBinding
    lateinit var toolbarLayout: ToolbarLayoutDetailBinding
    var detailListAdapter:GroupAdapter<GroupieViewHolder> = GroupAdapter()

    //    private var readmore = false
    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            updateGpsCheckUi(status)
        }
    }


    @Inject
    lateinit var locationHelper: LocationHelper

    @Inject
    lateinit var glide: RequestManager
    private lateinit var marker: Marker
    private var mapView: MapView? = null

//    private var mapFragment: SupportMapFragment? = null


    private val attractionDetailViewModel: AttractionDetailViewModel by viewModels()
    private fun subscribeToGpsListener() = attractionDetailViewModel.gpsStatusLiveData
        .observe(viewLifecycleOwner, gpsObserver)

    private var lat: String? = ""
    private var long: String? = ""
    private lateinit var attractionsObj: Attractions
    private lateinit var contentFlag: String

    private var isDetailFavouriteFlag = false

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(getCurrentLanguage().language)
            }
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationIsEmpty(locationResult.lastLocation)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
//        arguments?.apply {
//            attractionsObj = getParcelable(Constants.NavBundles.ATTRACTION_OBJECT)!!
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isPagerFragment = true
//        sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionDetailBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        detailInnerLayout = binding.attractionDetailInnerLayout
        toolbarLayout = binding.toolbarLayoutDetail
//        toolbarLayout.detailImageView.transitionName = attractionsObj.id
        subscribeUiEvents(attractionDetailViewModel)
        backArrowRTL(toolbarLayout.back)
        bgRTL(toolbarLayout.bgBorderUpper)
        backArrowRTL(binding.imgBack)
        arrowRTL(detailInnerLayout.arrowIbecons)
        arrowRTL(detailInnerLayout.arrowSiteMap)
        rvSetUp()
//        detailInnerLayout.tvDescReadmore.text = attractionsObj.description
        subscribeToGpsListener()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            callingObservables()
        }
        cardViewRTL()
        mapSetUp(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            runOnUiThread {
                callingObservables()
                locationPermission()
                subscribeObservables()
                uiActions()
            }
        }, 200)


    }


    private fun initializeDetails(attraction: Attractions) {


        binding.attraction = attraction
        if (this::marker.isInitialized) {
            marker.let {
                it.position = LatLng(
                    attraction.latitude?.toDouble()
                        ?: lat?.toDouble()!!, attraction.longitude?.toDouble()
                        ?: long?.toDouble()!!
                )
            }
        }


        if (attraction.IsFavourite) {
            toolbarLayout.favourite.background = getDrawableFromId(R.drawable.heart_icon_fav)
            binding.favourite.background = getDrawableFromId(R.drawable.heart_icon_fav)
        }

        binding.root.apply {
            attraction.let {
                binding.attractionDetailInnerLayout.tvReviews.text =
                    "${resources.getString(R.string.reviews)} ${it.title} ${
                        resources.getString(
                            R.string.on_trip
                        )
                    }"
                if (TextUtils.isEmpty(it.startDay) || TextUtils.isEmpty(it.endDay)) {
                    detailInnerLayout.tvAttractionDays.text = "Sunday - Friday"
                } else {
                    detailInnerLayout.tvAttractionDays.text =
                        "${attraction.startDay} - ${attraction.endDay}"
                }
                if (TextUtils.isEmpty(it.startTime) || TextUtils.isEmpty(it.endTime)) {
                    detailInnerLayout.tvTimes.text = "10:00 AM - 1:00 AM"
                } else {
                    detailInnerLayout.tvTimes.text =
                        "${attraction.startTime} - ${attraction.endTime}"
                }

            }

            attraction.apply {
                url = audioLink
                if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(latitude)) {
                    try {
                        val distance = locationHelper.distance(
                            lat!!.toDouble(), long!!.toDouble(),
                            latitude!!.toDouble(),
                            longitude!!.toDouble()
                        )
                        detailInnerLayout.tvKm.text = "$distance Km Away"
                    } catch (e: java.lang.NumberFormatException) {
                    }

                }

                detailListAdapter.apply {
                    if (this.itemCount > 0) {
                        this.clear()
                    }
                    events?.let { events ->
                        events.forEach {
                            add(
                                EventListItem<AttractionDetailUpComingItemsBinding>(
                                    object : FavouriteChecker {
                                        override fun checkFavListener(
                                            checkbox: CheckBox,
                                            pos: Int,
                                            isFav: Boolean,
                                            itemId: String,
                                        ) {
                                            favouriteClick(
                                                checkbox,
                                                isFav,
                                                R.id.action_attractionsFragment_to_postLoginFragment,
                                                itemId, attractionDetailViewModel,
                                                2
                                            )
                                        }

                                    },
                                    object : RowClickListener {
                                        override fun rowClickListener(position: Int) {
                                            navigate(R.id.action_attractionDetailFragment_to_eventDetailFragment2,
                                                Bundle().apply {
                                                    putParcelable(
                                                        Constants.NavBundles.EVENT_OBJECT,
                                                        it
                                                    )
                                                })
                                        }

                                        override fun rowClickListener(
                                            position: Int,
                                            imageView: ImageView
                                        ) {

                                        }

                                    },
                                    event = it,
                                    resLayout = R.layout.attraction_detail_up_coming_items,
                                    activity
                                )
                            )
                        }
                    }
                }

            }
        }
    }

    private fun callingObservables() {
        attractionsObj.let {
            attractionDetailViewModel.getAttractionDetailsToScreen(
                attractionId = it.id,
                getCurrentLanguage().language
            )
        }
    }

    private fun subscribeObservables() {
        attractionDetailViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {

                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        if (isDetailFavouriteFlag) {
                            binding.favourite.background =
                                getDrawableFromId(R.drawable.heart_icon_fav)
                            toolbarLayout.favourite.background =
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
                            toolbarLayout.favourite.background =
                                getDrawableFromId(R.drawable.heart_icon_home_black)
                            isDetailFavouriteFlag = false
                        }
                    }
                }
                is Result.Failure -> handleApiError(it, attractionDetailViewModel)
            }
        }

        attractionDetailViewModel.attractionDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    contentFlag = "ContentLoaded"
                    emailContact = it.value.emailContact
                    numberContact = it.value.numberContact
                    attractionsObj = it.value
                    if (it.value.gallery.isNullOrEmpty()) {
                        detailInnerLayout.downOneGallery.alpha = 0.4f
                        detailInnerLayout.downOneGallery.isClickable = false
                        toolbarLayout.llImg.isClickable = false
                    }
                    if (it.value.asset360?.imageItems.isNullOrEmpty()) {
                        toolbarLayout.ll360.alpha = 0.4f
                        detailInnerLayout.downOne360.alpha = 0.4f
                        toolbarLayout.ll360.isClickable = false
                        detailInnerLayout.downOne360.isClickable = false
                    }

                    if (numberContact.isNullOrEmpty()) {
                        detailInnerLayout.llCallUs.alpha = 0.4f
                        detailInnerLayout.llCallUs.isClickable = false
                    }
                    if (emailContact.isNullOrEmpty()) {
                        detailInnerLayout.llEmailus.alpha = 0.4f
                        detailInnerLayout.llEmailus.isClickable = false
                    }
                    detailInnerLayout.tvDescReadmore.text =
                        "${it.value.summary} ${""} ${it.value.description}"
                    initializeDetails(attractionsObj)
                }
                is Result.Failure -> {
                    handleApiError(it, attractionDetailViewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
        binding.appbarAttractionDetail
            .addOnOffsetChangedListener(this)


    }


    private fun uiActions() {
        toolbarLayout.llAr.setOnClickListener(this)
        toolbarLayout.ll360.setOnClickListener(this)
        toolbarLayout.llImg.setOnClickListener(this)
        toolbarLayout.back.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)
        toolbarLayout.btnBookATicket.setOnClickListener(this)
        detailInnerLayout.downOneAR.setOnClickListener(this)
        detailInnerLayout.downOne360.setOnClickListener(this)
        detailInnerLayout.downOneGallery.setOnClickListener(this)
        detailInnerLayout.imgAttractionSpeaker.setOnClickListener(this)
        detailInnerLayout.llEmailus.setOnClickListener(this)
        detailInnerLayout.llCallUs.setOnClickListener(this)
        detailInnerLayout.constLayoutSiteMap.setOnClickListener(this)
        detailInnerLayout.constLayoutIbecon.setOnClickListener(this)
        detailInnerLayout.tvDirection.setOnClickListener(this)
        detailInnerLayout.imgFb.setOnClickListener {
            openUrl(
                attractionsObj.socialLink?.get(0)!!.facebookPageLink,
                activity,
                isFacebook = true
            )
        }
        detailInnerLayout.instagram.setOnClickListener {
            openUrl(
                attractionsObj.socialLink?.get(0)!!.instagramPageLink,
                activity,
                isInstagram = true
            )
        }
        detailInnerLayout.imgTwitterAttraction.setOnClickListener {
            openUrl(
                attractionsObj.socialLink?.get(0)!!.twitterPageLink,
                activity,
                isTwitter = true
            )
        }
        detailInnerLayout.imgYoutube.setOnClickListener {
            openUrl(
                attractionsObj.socialLink?.get(0)!!.youtubePageLink,
                activity,
                isYoutube = true
            )
        }
        detailInnerLayout.imgLinkedinAttraction.setOnClickListener {
            openUrl(
                attractionsObj.socialLink?.get(0)!!.linkedInPageLink,
                activity,
                isLinkedIn = true
            )
        }

        binding.apply {


            root.apply {
                toolbarLayout.title.text = attractionsObj.title
                toolbarLayout.category.text = attractionsObj.category
                glide.load(BuildConfig.BASE_URL + attractionsObj.portraitImage)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in))
                    .into(toolbarLayout.detailImageView)
            }
            favourite.setOnClickListener {
                isDetailFavouriteFlag = true
                attractionsObj.let { attraction ->
                    favouriteClick(
                        favourite,
                        attraction.IsFavourite,
                        R.id.action_attractionDetailFragment_to_postLoginFragment,
                        attraction.id,
                        attractionDetailViewModel,
                        1
                    )
                }

            }
            share.setOnClickListener {
            }
            bookingCalender.setOnClickListener {
            }
            toolbarLayout.favourite.setOnClickListener {
                isDetailFavouriteFlag = true

                attractionsObj.let { attraction ->
                    favouriteClick(
                        favourite,
                        attraction.IsFavourite,
                        R.id.action_attractionDetailFragment_to_postLoginFragment,
                        attraction.id,
                        attractionDetailViewModel,
                        1
                    )
                }
            }
            toolbarLayout.share.setOnClickListener {
            }
            toolbarLayout.bookingCalender.setOnClickListener {
            }
        }
    }

    private fun rvSetUp() {
        detailInnerLayout.rvUpComing.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = detailListAdapter
        }
    }

    private fun cardViewRTL() {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        if (isArabic()) {
            detailInnerLayout.cardviewPlanTrip?.shapeAppearanceModel =
                detailInnerLayout.cardviewPlanTrip!!.shapeAppearanceModel
                    .toBuilder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .setTopRightCornerSize(radius)
                    .build()
        } else {
            detailInnerLayout.cardviewPlanTrip.shapeAppearanceModel =
                detailInnerLayout.cardviewPlanTrip.shapeAppearanceModel
                    .toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                    .setBottomRightCornerSize(radius)
                    .build()
        }
    }

    private fun mapSetUp(savedInstanceState: Bundle?) {

        mapView = binding.root.findViewById(R.id.map)
        mapView?.let {
            it.getMapAsync(this)
            it.onCreate(savedInstanceState)

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
                    override fun getCurrentLocation(location: Location) {
                        Timber.e("Current Location ${location.latitude}")
                        lat = location.latitude.toString()
                        long = location.longitude.toString()

                        if (!TextUtils.isEmpty(attractionsObj.latitude) && !TextUtils.isEmpty(
                                attractionsObj.latitude
                            )
                        ) {
                            val distance =
                                locationHelper.distance(
                                    lat!!.toDouble(), long!!.toDouble(),
                                    attractionsObj.latitude?.toDouble()!!,
                                    attractionsObj.longitude?.toDouble()!!
                                )
                            detailInnerLayout.tvKm.text = "$distance Km Away"
                        }

                    }
                },
//                activity,
                locationCallback = locationCallback
            )
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        try {

            if (!attractionsObj.latitude.isNullOrEmpty() && !attractionsObj.longitude.isNullOrEmpty()) {
                val attractionLatLng = LatLng(
                    attractionsObj.latitude?.toDouble()!!,
                    attractionsObj.longitude?.toDouble()!!
                )


                map?.addMarker(
                    MarkerOptions()
                        .position(attractionLatLng)
                        .title(
                            attractionsObj.title
                        )
                        .icon(fromResource(R.drawable.pin_location))
                )
                map?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        attractionLatLng, 14.0f
                    )
                )
                map?.cameraPosition?.target

            }
        } catch (e: NumberFormatException) {
            e.stackTrace
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_direction -> {
                if (!attractionsObj.latitude.isNullOrEmpty()) {
                    val uri =
                        "http://maps.google.com/maps?saddr=" + lat.toString() + "," + long.toString() + "&daddr=" + attractionsObj.latitude.toString() + "," + attractionsObj.longitude
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage("com.google.android.apps.maps")
                    try {
                        startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {
                        attractionDetailViewModel.showToast("Please install a maps application")
                    }
                }
            }
            R.id.constLayoutSiteMap -> {
                val bundle = Bundle()
                bundle.putParcelable(
                    Constants.NavBundles.SITE_MAP_OBJ,
                    attractionsObj.siteMap
                )
                navigate(R.id.action_attractionDetailFragment_to_siteMapFragment, bundle)
//                navigateByDirections(AttractionDetailFragmentDirections.actionAttractionDetailFragmentToSiteMapFragment(attractionsObj.siteMap))
            }
            R.id.constLayoutIbecon -> {
                if (SystemRequirementsHelper.isLocationServiceForBluetoothLeEnabled(requireContext()) && SystemRequirementsHelper.isBluetoothEnabled(
                        requireContext()
                    )
                ) {
                    val bundle = bundleOf(ATTRACTION_ID to attractionsObj.id)
                    navigate(R.id.action_attractionDetailFragment_to_ibeconFragment, bundle)
                } else if (!SystemRequirementsHelper.isBluetoothEnabled(requireContext())) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBtIntent, 1)
                } else {
                    SystemRequirementsChecker.checkWithDefaultDialogs(requireActivity())
                }

//                navigateByDirections(AttractionDetailFragmentDirections.actionAttractionDetailFragmentToIbeconFragment(attractionsObj.id))
            }
            R.id.img_attraction_speaker -> {
                if (detailInnerLayout.tvDescReadmore.text.isNotEmpty()) {
                    textToSpeechEngine.speak(
                        detailInnerLayout.tvDescReadmore.text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1"
                    )
                }
            }
            R.id.ll_ar -> {
                activity.runWithPermissions(
                    Manifest.permission.CAMERA,
                ) {
                    navigate(R.id.action_attractionDetailFragment_to_ARFragment)
                }
            }
            R.id.ll_360 -> {
                navigate(R.id.action_attractionDetailFragment_to_threeSixtyFragment,
                    Bundle().apply {
                        putParcelable(THREESIXTY_GALLERY_LIST, attractionsObj)
                    })
            }
            R.id.ll_img -> {
                navigate(R.id.action_attractionDetailFragment_to_attractionGalleryFragment,
                    Bundle().apply {
                        attractionsObj.gallery?.let {
                            putParcelableArrayList(
                                ATTRACTION_GALLERY_LIST,
                                it as ArrayList<out Parcelable>
                            )
                        }
                    })
            }
            R.id.back -> {
                back()
            }
            R.id.img_back -> {
                back()
            }
            R.id.btn_book_a_ticket -> {
//                attractionDetailViewModel.showToast("Book a Ticket")
            }
            R.id.downOneAR -> {
                activity.runWithPermissions(
                    Manifest.permission.CAMERA,
                ) {
                    navigate(R.id.action_attractionDetailFragment_to_ARFragment)
                }

            }
            R.id.downOne360 -> {
                navigate(R.id.action_attractionDetailFragment_to_threeSixtyFragment,
                    Bundle().apply {
                        putParcelable(THREESIXTY_GALLERY_LIST, attractionsObj)
                    })
            }
            R.id.downOneGallery -> {
                navigate(R.id.action_attractionDetailFragment_to_attractionGalleryFragment,
                    Bundle().apply {
                        attractionsObj.gallery?.let {
                            putParcelableArrayList(
                                ATTRACTION_GALLERY_LIST,
                                it as ArrayList<out Parcelable>
                            )
                        }
                    })
            }
            R.id.ll_call_us -> {
                if (!numberContact.isNullOrEmpty()) {
                    openDiallerBox(numberContact)
                }
            }
            R.id.ll_emailus -> {
                if (!emailContact.toString().isNullOrEmpty()) {
                    openEmailbox(emailContact.toString())
                }
            }
        }
    }

    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
        mapView?.onPause()
        binding.appbarAttractionDetail.removeOnOffsetChangedListener(this)

    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//
//        if (mapFragment != null) childFragmentManager.beginTransaction().remove(mapFragment!!)
//            .commitAllowingStateLoss()
//    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
        mapView?.onDestroy()
    }

    private fun updateGpsCheckUi(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                locationPermission()
            }
            is GpsStatus.Disabled -> {
                attractionDetailViewModel.showToast(message = "Please enable location !")
            }
        }
    }

    private fun locationIsEmpty(location: Location) {
        try {
            if (!TextUtils.isEmpty(attractionsObj.latitude) && !TextUtils.isEmpty(
                    attractionsObj.latitude
                )
            ) {
                val distance =
                    locationHelper.distance(
                        location.latitude, location.longitude,
                        attractionsObj.latitude!!.toDouble(),
                        attractionsObj.longitude!!.toDouble()
                    )
                detailInnerLayout.tvKm.text = "$distance Km Away"
            }
        } catch (e: java.lang.NumberFormatException) {
        }

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == -toolbarLayout.collapsingToolbarAttractionDetail.height + toolbarLayout.toolbarAttractionDetail.height) {
            binding.defaultCloseToolbar.visibility = View.VISIBLE
            binding.swipeRefreshLayout.isEnabled = false

        } else {
            binding.defaultCloseToolbar.visibility = View.GONE
            binding.swipeRefreshLayout.isEnabled = true

        }
    }


}