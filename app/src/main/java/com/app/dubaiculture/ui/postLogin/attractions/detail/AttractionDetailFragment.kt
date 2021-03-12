package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentAttractionDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.adapter.UpComingItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.AttractionDetailViewModel
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.EventDetailViewModel
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
import kotlinx.android.synthetic.main.fragment_attraction_detail.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.bookingCalender
import kotlinx.android.synthetic.main.toolbar_layout_detail.favourite
import kotlinx.android.synthetic.main.toolbar_layout_detail.share
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*
import timber.log.Timber


@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>(),
    OnMapReadyCallback {
    private val attractionDetailViewModel: AttractionDetailViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionDetailBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(attractionDetailViewModel)
        uiActions()
        mapSetUp()
        rvSetUp()
        cardViewRTL()
    }

    private fun uiActions() {

        binding.apply {
            appbarAttractionDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (verticalOffset == -binding.root.collapsingToolbarAttractionDetail.height + binding.root.toolbarAttractionDetail.height) {
                    Timber.e(verticalOffset.toString())
                    //toolbar is collapsed here
                    //write your code here
                    defaultCloseToolbar.visibility = View.VISIBLE
//                    img.visibility = View.VISIBLE
//                    imageView4.visibility = View.VISIBLE
                } else {
                    defaultCloseToolbar.visibility = View.GONE
//                    imageView4.visibility = View.GONE

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

        }
    }

    private fun collapseAppbar(boolean: Boolean = false) {
        binding.appbarAttractionDetail.setExpanded(boolean)
    }

    private fun rvSetUp() {
        binding.root.rv_up_coming.apply {
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
    private fun cardViewRTL(){
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        if(isArabic()){
            binding.root.cardview_plan_trip?.shapeAppearanceModel =  binding.root.cardview_plan_trip!!.shapeAppearanceModel
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED,radius)
                .setTopRightCornerSize(radius)
                .build()
        }else{
            binding.root.cardview_plan_trip?.shapeAppearanceModel =  binding.root.cardview_plan_trip!!.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED,radius)
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
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                trafficDigitalLatLng, 12.0f
            )
        )
        map.cameraPosition.target


    }
}