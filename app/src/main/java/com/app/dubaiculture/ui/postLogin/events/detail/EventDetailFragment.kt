package com.app.dubaiculture.ui.postLogin.events.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentEventDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.adapter.UpComingItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.EventDetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.attraction_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.event_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.toolbarAttractionDetail
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.bookingCalender
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.collapsingToolbarAttractionDetail
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.favourite
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.share
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.*
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.view.*
import timber.log.Timber

class EventDetailFragment : BaseFragment<FragmentEventDetailBinding>() ,
    OnMapReadyCallback {
    private val eventDetailViewModel: EventDetailViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) =FragmentEventDetailBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapSetUp()
        uiActions()
        rvSetUp()
    }


    private fun uiActions() {

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
            favourite.setOnClickListener {
                eventDetailViewModel.showToast("favourite Clicked")
            }
            share.setOnClickListener {
                eventDetailViewModel.showToast("share Clicked")
            }
            bookingCalender.setOnClickListener {
                eventDetailViewModel.showToast("bookingCalender Clicked")
            }
            toolbarEventDetail.favourite.setOnClickListener {
                eventDetailViewModel.showToast("favourite Toolbar Clicked")
            }
            toolbarEventDetail.share.setOnClickListener {
                eventDetailViewModel.showToast("share Toolbar Clicked")
            }
            toolbarEventDetail.bookingCalender.setOnClickListener {
                eventDetailViewModel.showToast("bookingCalender Toolbar Clicked")
            }

        }
    }
      private   fun rvSetUp() {
            binding.root.rv_event_up_coming.apply {
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
    private fun mapSetUp() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)

    }
    override fun onMapReady(map: GoogleMap?) {
        val trafficDigitalLatLng = LatLng(24.8623, 67.0627)
        map?.addMarker(MarkerOptions()
            .position(trafficDigitalLatLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location)))!!
            .title = "Traffic Digital"
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                trafficDigitalLatLng, 12.0f
            )
        )
        map.cameraPosition.target
    }
}