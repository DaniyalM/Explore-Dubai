package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
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
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils.instagramNavigationIntent
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.handleApiError
import com.bumptech.glide.RequestManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.shape.CornerFamily
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.attraction_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>(),
    OnMapReadyCallback, View.OnClickListener {

    private var url: String? = null
    private var isPLAYING = false

    @Inject
    lateinit var glide: RequestManager
    private val attractionDetailViewModel: AttractionViewModel by viewModels()

    //    private var detailImage: String? = null
//    private var detailTitle: String? = null
//    private var detailCategory: String? = null
//    private var detailId: String? = null
    private var lat: String? = 24.8623.toString()
    private var long: String? = 67.0627.toString()
    private var attractionsObj: Attractions? = null
    var mp: MediaPlayer? = MediaPlayer()
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(getCurrentLanguage().language)
            }
        }
    }

    private fun stopPlaying() {
        mp?.release()
        mp = null
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
        rvSetUp()
        callingObservables()
        subscribeObservables()
        uiActions()
        mapSetUp()
        cardViewRTL()
    }


    private fun initializeDetails(attraction: Attractions) {
        binding.attraction = attraction

        binding.root.apply {
            attraction.apply {
                url = audioLink
                lat = latitude
                long = longitude
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
                                        ) {
                                            favouriteEvent(application.auth.isGuest,
                                                checkbox,
                                                isFav,
                                                R.id.action_attractionDetailFragment_to_postLoginFragment)
                                        }
                                    }, object : RowClickListener {
                                        override fun rowClickListener(position: Int) {
//                                            navigate(R.id.action_eventFilterFragment_to_eventDetailFragment2)
                                        }

                                    }, event = it,
                                    resLayout = R.layout.attraction_detail_up_coming_items)
//                            UpComingItems(event=it)
                            )
                        }
                    }
                }


            }


        }
        mp?.setOnPreparedListener { mPlayer -> mPlayer?.start() }
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
        attractionDetailViewModel.isPlaying.observe(viewLifecycleOwner) {
            if (it) {
                try {
                    mp?.let {
                        it.setDataSource(url)
                        it.prepareAsync();
                        it.start()
                    }

                } catch (e: IOException) {
                    Timber.e("prepare() failed")
                }
            } else {
                stopPlaying()
            }
        }
        attractionDetailViewModel.attractionDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    initializeDetails(it.value)
                }
                is Result.Failure -> {
                    handleApiError(it, attractionDetailViewModel)
                }
            }
        }
    }

    private fun uiActions() {
        binding.apply {
            root.apply {
                title.text = attractionsObj?.title
                category.text = attractionsObj?.category
                glide.load(BuildConfig.BASE_URL + attractionsObj?.portraitImage)
                    .into(detailImageView)
            }
            appbarAttractionDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (verticalOffset == -binding.root.collapsingToolbarAttractionDetail.height + binding.root.toolbarAttractionDetail.height) {
                    Timber.e(verticalOffset.toString())
                    //toolbar is collapsed here
                    //write your code here
                    defaultCloseToolbar.visibility = View.VISIBLE
                    //                    img.visibility = View.VISIBLE
                    //                    imageView.visibility = View.VISIBLE
                } else {
                    defaultCloseToolbar.visibility = View.GONE
                    //                    imageView.visibility = View.GONE
                    //                    img.visibility = View.GONE

                }
            })
            favourite.setOnClickListener {
                attractionDetailViewModel.showToast("favourite Clicked")
            }
            share.setOnClickListener {
                attractionDetailViewModel.showToast("share Clicked")
            }
            bookingCalender.setOnClickListener {
                attractionDetailViewModel.showToast("bookingCalender Clicked")
            }
            toolbarAttractionDetail.favourite.setOnClickListener {
                attractionDetailViewModel.showToast("favourite Toolbar Clicked")
            }
            toolbarAttractionDetail.share.setOnClickListener {
                attractionDetailViewModel.showToast("share Toolbar Clicked")
            }
            toolbarAttractionDetail.bookingCalender.setOnClickListener {
                attractionDetailViewModel.showToast("bookingCalender Toolbar Clicked")
            }

            //            binding.imgBack.setOnClickListener {
            //                back()
            //            }

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
            binding!!.root.cardview_plan_trip?.shapeAppearanceModel =
                binding!!.root.cardview_plan_trip!!.shapeAppearanceModel
                    .toBuilder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .setTopRightCornerSize(radius)
                    .build()
        } else {
            binding!!.root.cardview_plan_trip?.shapeAppearanceModel =
                binding!!.root.cardview_plan_trip!!.shapeAppearanceModel
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
                navigate(R.id.action_attractionDetailFragment_to_threeSixtyFragment)
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
                navigate(R.id.action_attractionDetailFragment_to_threeSixtyFragment)

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