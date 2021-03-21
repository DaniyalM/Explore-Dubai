package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentAttractionDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.adapter.UpComingItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.AttractionDetailViewModel
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
import javax.inject.Inject


@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>(),
    OnMapReadyCallback, View.OnClickListener {

    @Inject
    lateinit var glide: RequestManager
    private val attractionDetailViewModel: AttractionDetailViewModel by viewModels()
    private var detailImage: String? = null
    private var detailTitle: String? = null
    private var detailCategory: String? = null
    private var detailId: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            getString(AttractionListingFragment.ATTRACTION_DETAIL_ID)?.let {
                detailId = it
            }
            getString(AttractionListingFragment.ATTRACTION_DETAIL_IMAGE)?.let {
                detailImage = it
            }
            getString(AttractionListingFragment.ATTRACTION_DETAIL_TITLE)?.let {
                detailTitle = it
            }
            getString(AttractionListingFragment.ATTRACTION_DETAIL_CATEGORY)?.let {
                detailCategory = it
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionDetailBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(attractionDetailViewModel)
        binding!!.let {
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
        }

        uiActions()
        mapSetUp()
        rvSetUp()
        cardViewRTL()
    }

    private fun uiActions() {
        binding.apply {
            root.apply {
                title.text = detailTitle
                category.text = detailCategory
                glide.load(BuildConfig.BASE_URL + detailImage).into(detailImageView)
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

    private fun collapseAppbar(boolean: Boolean = false) {
        binding!!.appbarAttractionDetail.setExpanded(boolean)
    }

    private fun rvSetUp() {
        binding!!.root.rv_up_coming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
            groupAdapter.apply {
                add(UpComingItems("Free",
                    R.drawable.must_see_icon_home,
                    "14",
                    "NOV, 20",
                    "20",
                    "NOV, 20",
                    "Workshop",
                    "The Definitive Guide to an Uncertain World",
                    "Palm Jumeriah, Dubai"))
                add(UpComingItems("Free",
                    R.drawable.must_see_icon_home,
                    "14",
                    "NOV, 20",
                    "20",
                    "NOV, 20",
                    "Workshop",
                    "The Definitive Guide to an Uncertain World",
                    "Palm Jumeriah, Dubai"))
                add(UpComingItems("Free",
                    R.drawable.must_see_icon_home,
                    "14",
                    "NOV, 20",
                    "20",
                    "NOV, 20",
                    "Workshop",
                    "The Definitive Guide to an Uncertain World",
                    "Palm Jumeriah, Dubai"))
            }
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
        val trafficDigitalLatLng = LatLng(24.8623, 67.0627)
        map!!.addMarker(MarkerOptions()
            .position(trafficDigitalLatLng)
            .icon(fromResource(R.drawable.pin_location)))
            .title = "Traffic Digital"
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                trafficDigitalLatLng, 12.0f
            )
        )
        map.cameraPosition.target


    }

    override fun onClick(v: View?) {
        when (v?.id) {
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
        }
    }
}