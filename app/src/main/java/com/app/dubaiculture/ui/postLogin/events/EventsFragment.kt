package com.app.dubaiculture.ui.postLogin.events

import android.Manifest
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.GpsStatus
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.material.shape.CornerFamily
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.plan_a_trip_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>() {
    private lateinit var eventAdapter: EventAdapter
    private lateinit var moreAdapter: EventAdapter
    private lateinit var nearAdapter: EventAdapter
    private val eventViewModel: EventViewModel by viewModels()

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var locationRequest: LocationRequest

    @Inject
    lateinit var geocoder: Geocoder


    @Inject
    lateinit var glide: RequestManager

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            updateGpsCheckUI(status)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventsBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(eventViewModel)
        rvSetUp()
        cardViewRTL()
        setupToolbarWithSearchItems()
        subscribeToGpsListener()
        locationPermission()
//        binding.swipeRefresh.setOnRefreshListener {
//            binding.swipeRefresh.isRefreshing = false
//        }
        binding.tvViewMap.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventNearMapFragment2)
        }

    }

    private fun subscribeToGpsListener() = eventViewModel.gpsStatusLiveData
        .observe(viewLifecycleOwner, gpsObserver)

    private fun rvSetUp() {
        eventAdapter = EventAdapter(object : FavouriteChecker {
            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
                favouriteEvent(application.auth.isGuest, checkbox, isFav,R.id.action_eventsFragment_to_postLoginFragment)
            }
        }, object : RowClickListener {
            override fun rowClickListener() {
                navigate(R.id.action_eventsFragment_to_eventFilterFragment)

            }

        })
        moreAdapter = EventAdapter(object : FavouriteChecker {
            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
                favouriteEvent(application.auth.isGuest, checkbox, isFav,R.id.action_eventsFragment_to_postLoginFragment)
            }
        }, object : RowClickListener {
            override fun rowClickListener() {
                navigate(R.id.action_eventsFragment_to_eventFilterFragment)
            }
        })
        nearAdapter = EventAdapter(object : FavouriteChecker {
            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
                favouriteEvent(application.auth.isGuest, checkbox, isFav,R.id.action_eventsFragment_to_postLoginFragment)
            }
        }, object : RowClickListener {
            override fun rowClickListener() {
                navigate(R.id.action_eventsFragment_to_eventFilterFragment)
            }
        })
        binding.rvEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapter

        }
        eventAdapter.events = createEventItems()

        binding.rvMoreEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moreAdapter
        }

        moreAdapter.events = createEventItems()

        binding.rvNearEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nearAdapter

        }
        nearAdapter.events = createEventItems()

    }

    private fun setupToolbarWithSearchItems() {
        binding.root.apply {
            profilePic.visibility = View.GONE
            img_drawer.visibility = View.GONE
            toolbar_title.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.events)
            }
        }
    }

    private fun cardViewRTL() {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        if (isArabic()) {
            binding.root.cardivewRTL?.shapeAppearanceModel =
                binding.root.cardivewRTL!!.shapeAppearanceModel
                    .toBuilder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .setTopRightCornerSize(radius)
                    .build()
        } else {
            binding.root.cardivewRTL?.shapeAppearanceModel =
                binding.root.cardivewRTL!!.shapeAppearanceModel
                    .toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                    .setBottomRightCornerSize(radius)
                    .build()
        }
    }

    private fun createTestItems(): List<EventHomeListing> =
        mutableListOf<EventHomeListing>().apply {


            repeat((1..2).count()) {
                when (it % 2) {
                    0 -> {
                        add(
                            EventHomeListing(
                                title = "FeatureEvents",
                                category = "FeatureEvents",
                                events = createEventItems()
                            )
                        )
                    }
                    else -> {
                        add(
                            EventHomeListing(
                                title = "More Events",
                                category = "MoreEvents",
                                events = createEventItems()
                            )
                        )
                    }
                }


            }
        }

    private fun createEventItems(): ArrayList<Events> = mutableListOf<Events>().apply {
        repeat((1..4).count()) {
            add(
                Events(
                    id = it.toString(),
                    title = "The Definitive Guide to an Uncertain World",
                    category = "Workshop",
                    fromDate = "18",
                    fromMonthYear = "Mar, 21",
                    fromTime = "20$it",
                    fromDay = "1$it",
                    toDate = "20",
                    toMonthYear = "Mar, 21",
                    toTime = "202$it",
                    toDay = "2$it",
                    type = "FREE",
                    locationTitle = "Palm Jumeriah, Dubai",
                    isFavourite = false
                )
            )
        }
    } as ArrayList<Events>


    private fun locationPermission() {
        val quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = false
        )
        activity.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            options = quickPermissionsOption
        ) {
            // will work on fused location API
        }
    }

    private fun updateGpsCheckUI(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                binding.tvViewMap.visibility = View.VISIBLE
                binding.rvNearEvent.visibility = View.VISIBLE
                binding.root.cardivewRTL.visibility = View.GONE
            }
            is GpsStatus.Disabled -> {
                binding.tvViewMap.visibility = View.GONE
                binding.rvNearEvent.visibility = View.GONE
                binding.root.cardivewRTL.visibility = View.VISIBLE
                eventViewModel.showAlert(message = resources.getString(R.string.please_enable_gps))
            }
        }

    }
}