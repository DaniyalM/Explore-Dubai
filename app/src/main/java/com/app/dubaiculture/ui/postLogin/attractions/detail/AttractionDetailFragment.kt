package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionDetailUpComingItemsBinding
import com.app.dubaiculture.databinding.FragmentAttractionDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils.instagramNavigationIntent
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_GALLERY_LIST
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.shape.CornerFamily
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.attraction_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>(),
    OnMapReadyCallback, View.OnClickListener {
    private var url: String? = null

    @Inject
    lateinit var locationHelper: LocationHelper

    @Inject
    lateinit var glide: RequestManager
    private val attractionDetailViewModel: AttractionViewModel by viewModels()
    private var lat: String? = 24.8623.toString()
    private var long: String? = 67.0627.toString()
    private lateinit var attractionsObj: Attractions
    private lateinit var contentFlag: String
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(getCurrentLanguage().language)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            attractionsObj = getParcelable(Constants.NavBundles.ATTRACTION_OBJECT)!!
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionDetailBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(attractionDetailViewModel)
        locationPermission()

    
        if (!this::contentFlag.isInitialized) {
            rvSetUp()
            callingObservables()
            subscribeObservables()
        }
       
        uiActions()
        mapSetUp()
        cardViewRTL()
    }
    

    private fun initializeDetails(attraction: Attractions) {


        binding.attraction = attraction
        binding.root.apply {
            attraction.let {
                if (TextUtils.isEmpty(it.startDay) || TextUtils.isEmpty(it.endDay)) {
                    tv_attraction_days.text = "Sunday - Friday"
                } else {
                    tv_attraction_days.text = "${attraction.startDay} - ${attraction.endDay}"
                }
                if (TextUtils.isEmpty(it.startTime) || TextUtils.isEmpty(it.endTime)) {
                    tv_times.text = "10:00 AM - 1:00 AM"
                } else {
                    tv_times.text = "${attraction.startTime} - ${attraction.endTime}"
                }

            }

            attraction.apply {
                url = audioLink
                if (!TextUtils.isEmpty(latitude)&&!TextUtils.isEmpty(latitude)){
                    val distance = locationHelper.distance(lat!!.toDouble(), long!!.toDouble(),
                        latitude!!.toDouble(),
                        longitude!!.toDouble())
                    tv_km.text = "$distance Km Away"
                }


                instagram.setOnClickListener {
                    startActivity(instagramNavigationIntent(activity.packageManager))
                }
                groupAdapter.apply {
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
                                                itemId, attractionDetailViewModel
                                            )
                                        }

                                    }, object : RowClickListener {
                                        override fun rowClickListener(position: Int) {
                                            navigate(R.id.action_attractionDetailFragment_to_eventDetailFragment2,
                                                Bundle().apply {
                                                    putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                                        it)
                                                })
                                        }

                                    }, event = it,
                                    resLayout = R.layout.attraction_detail_up_coming_items)
                            )
                        }
                    }
                }

            }


        }
    }

    private fun callingObservables() {
        attractionsObj?.let {
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
//                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_fav)
                        binding.favourite.background = getDrawableFromId(R.drawable.heart_icon_fav)
                        binding.root.favourite.background =
                            getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
//                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_home_black)
                        binding.favourite.background =
                            getDrawableFromId(R.drawable.heart_icon_home_black)
                        binding.root.favourite.background =
                            getDrawableFromId(R.drawable.heart_icon_home_black)
                    }
                }
                is Result.Failure -> handleApiError(it, attractionDetailViewModel)
            }
        }

        attractionDetailViewModel.attractionDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    contentFlag = "ContentLoaded"

                    attractionsObj = it.value
                    initializeDetails(attractionsObj!!)
                }
                is Result.Failure -> {
                    handleApiError(it, attractionDetailViewModel)
                }
            }
        }
    }

    private fun uiActions() {
        binding.let {

            it.root.ll_ar.setOnClickListener(this)
            it.root.ll_360.setOnClickListener(this)
            it.root.ll_img.setOnClickListener(this)
            it.root.back.setOnClickListener(this)
            it.imgBack.setOnClickListener(this)
            it.root.btn_book_a_ticket.setOnClickListener(this)
            it.root.tv_swipe_up.setOnClickListener(this)
            it.root.downOneAR.setOnClickListener(this)
            it.root.downOne360.setOnClickListener(this)
            it.root.downOneGallery.setOnClickListener(this)
            it.root.img_attraction_speaker.setOnClickListener(this)
            it.root.ll_emailus.setOnClickListener(this)
            it.root.ll_call_us.setOnClickListener(this)

        }
        binding.apply {
            root.apply {
                title.text = attractionsObj?.title
                category.text = attractionsObj?.category
                glide.load(BuildConfig.BASE_URL + attractionsObj?.portraitImage)
                    .into(detailImageView)
            }
            appbarAttractionDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (verticalOffset == -binding.root.collapsingToolbarAttractionDetail.height + binding.root.toolbarAttractionDetail.height) {
                    defaultCloseToolbar.visibility = View.VISIBLE
                } else {
                    defaultCloseToolbar.visibility = View.GONE
                }
            })
            favourite.setOnClickListener {
                attractionsObj?.let { attraction ->
                    favouriteClick(it.favourite,
                        attraction.IsFavourite,
                        R.id.action_attractionDetailFragment_to_postLoginFragment,
                        attraction.id,
                        attractionDetailViewModel,
                        1)
                }

            }
            share.setOnClickListener {
            }
            bookingCalender.setOnClickListener {
            }
            toolbarAttractionDetail.favourite.setOnClickListener {
                attractionsObj?.let { attraction ->
                    favouriteClick(it.favourite,
                        attraction.IsFavourite,
                        R.id.action_attractionDetailFragment_to_postLoginFragment,
                        attraction.id,
                        attractionDetailViewModel,
                        1)
                }
            }
            toolbarAttractionDetail.share.setOnClickListener {
            }
            toolbarAttractionDetail.bookingCalender.setOnClickListener {
            }
        }
    }

    private fun rvSetUp() {
        binding.root.rv_up_coming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
        }
    }

    private fun cardViewRTL() {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        if (isArabic()) {
            binding.root.cardview_plan_trip?.shapeAppearanceModel =
                binding.root.cardview_plan_trip!!.shapeAppearanceModel
                    .toBuilder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .setTopRightCornerSize(radius)
                    .build()
        } else {
            binding.root.cardview_plan_trip?.shapeAppearanceModel =
                binding.root.cardview_plan_trip!!.shapeAppearanceModel
                    .toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                    .setBottomRightCornerSize(radius)
                    .build()
        }
    }

    private fun mapSetUp() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)

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
                    }
                },
                object : LocationHelper.LocationCallBack {
                    override fun getLocationResultCallback(locationResult: LocationResult?) {
                        Timber.e("LocationResult ${locationResult!!.lastLocation.latitude}")
                    }

                }, activity)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        try {
            val attractionLatLng = LatLng(lat?.toDouble()!!, long?.toDouble()!!)
            map!!.addMarker(MarkerOptions()
                .position(attractionLatLng)
                .icon(fromResource(R.drawable.pin_location)))
                .title = "Traffic Digital"
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    attractionLatLng, 14.0f
                )
            )
            map.cameraPosition.target
        } catch (e: NumberFormatException) {
            e.stackTrace
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_attraction_speaker -> {
                if (binding.root.tv_desc_readmore.text.isNotEmpty()) {
                    textToSpeechEngine.speak(binding.root.tv_desc_readmore.text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1")
                }
            }
            R.id.ll_ar -> {
                attractionDetailViewModel.showToast("AR")
            }
            R.id.ll_360 -> {
                navigate(R.id.action_attractionDetailFragment_to_threeSixtyFragment)
            }
            R.id.ll_img -> {
                navigate(R.id.action_attractionDetailFragment_to_attractionGalleryFragment,
                    Bundle().apply {
                        attractionsObj?.gallery?.let {
                            putParcelableArrayList(ATTRACTION_GALLERY_LIST,
                                it as ArrayList<out Parcelable>)
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
                attractionDetailViewModel.showToast("Book a Ticket")
            }
            R.id.tv_swipe_up -> {
                attractionDetailViewModel.showToast("Swipe up")
            }
            R.id.downOneAR -> {
                attractionDetailViewModel.showToast("AR")

            }
            R.id.downOne360 -> {
                navigate(R.id.action_attractionDetailFragment_to_threeSixtyFragment)

            }
            R.id.downOneGallery -> {
                navigate(R.id.action_attractionDetailFragment_to_attractionGalleryFragment,
                    Bundle().apply {
                        attractionsObj?.gallery?.let {
                            putParcelableArrayList(ATTRACTION_GALLERY_LIST,
                                it as ArrayList<out Parcelable>)
                        }

                    })

            }
            R.id.ll_call_us -> {
                openDiallerBox("123123123")
            }
            R.id.ll_emailus -> {
                openEmailbox("test@gmail.com")

            }

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