package com.app.dubaiculture.ui.postLogin.events.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentEventDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.adapter.UpComingItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.EventDetailViewModel
import com.app.dubaiculture.ui.postLogin.events.adapters.EventScheduleItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.event_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.event_detail_schedule_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.view.*
import timber.log.Timber


@AndroidEntryPoint
class EventDetailFragment : BaseFragment<FragmentEventDetailBinding>(),
    OnMapReadyCallback , View.OnClickListener {
    private val eventDetailViewModel: EventDetailViewModel by viewModels()
    lateinit var scheduleAdapter: GroupAdapter<GroupieViewHolder>

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventDetailBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(eventDetailViewModel)

        mapSetUp()
        uiActions()
        rvSetUp()

        binding.root.btn_register_now.setOnClickListener{

        }
        binding.root.tv_direction.setOnClickListener {

        }
        binding.root.ll_callus.setOnClickListener {

        }
        binding.root.ll_email_us.setOnClickListener {

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
        binding.root.btn_reg.setOnClickListener(this)
        binding.root.back_event.setOnClickListener(this)
        binding.root.tv_swipe_up_event.setOnClickListener(this)
        binding.root.img_share_event.setOnClickListener(this)
        binding.root.bookingCalender_event.setOnClickListener(this)
        binding.root.favourite_event.setOnClickListener(this)

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
//            groupAdapter.apply {
//                add(UpComingItems("FREE",
//                    R.drawable.must_see_icon_home,
//                    "14",
//                    "NOV, 20",
//                    "20",
//                    "NOV, 20",
//                    "Workshop",
//                    "The Definitive Guide to an Uncertain World",
//                    "Palm Jumeriah, Dubai"))
//                add(UpComingItems("FREE",
//                    R.drawable.must_see_icon_home,
//                    "14",
//                    "NOV, 20",
//                    "20",
//                    "NOV, 20",
//                    "Workshop",
//                    "The Definitive Guide to an Uncertain World",
//                    "Palm Jumeriah, Dubai"))
//                add(UpComingItems("FREE",
//                    R.drawable.must_see_icon_home,
//                    "14",
//                    "NOV, 20",
//                    "20",
//                    "NOV, 20",
//                    "Workshop",
//                    "The Definitive Guide to an Uncertain World",
//                    "Palm Jumeriah, Dubai"))
//            }
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_reg -> {
                navigate(R.id.action_eventDetailFragment2_to_registerNowFragment)
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
                eventDetailViewModel.showToast("Favourite")
            }
        }
    }

}