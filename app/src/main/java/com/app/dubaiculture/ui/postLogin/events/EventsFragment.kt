package com.app.dubaiculture.ui.postLogin.events

import android.Manifest
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.StaticLatLng.LAT
import com.app.dubaiculture.utils.Constants.StaticLatLng.LNG
import com.app.dubaiculture.utils.GpsStatus
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.material.shape.CornerFamily
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_events.view.*
import kotlinx.android.synthetic.main.plan_a_trip_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>() {
    private lateinit var featureAdapter: EventAdapter
    private lateinit var eventAdapter: EventAdapter
    private lateinit var moreAdapter: EventAdapter
    private val eventViewModel: EventViewModel by viewModels()

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var locationRequest: LocationRequest


    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var locationHelper: LocationHelper
//    val locationHelper = LocationHelper

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            updateGpsCheckUI(status)
        }
    }

//    private val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            Timber.e("CallBack => ${locationResult.lastLocation.toString()}")
//        }
//    }

    companion object {
        val nearEventList = ArrayList<Events>()

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
        callingObservables()
        subscribeToObservables()
//        binding.swipeRefresh.setOnRefreshListener {
//            binding.swipeRefresh.isRefreshing = false
//        }
        binding!!.tvViewMap.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventNearMapFragment2)
        }
        binding!!.root.view_all_events.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventFilterFragment)
        }
    }

    private fun subscribeToGpsListener() = eventViewModel.gpsStatusLiveData
        .observe(viewLifecycleOwner, gpsObserver)

    private fun rvSetUp() {
        featureAdapter = EventAdapter(object : FavouriteChecker {
            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
                favouriteEvent(application.auth.isGuest,
                    checkbox,
                    isFav,
                    R.id.action_eventsFragment_to_postLoginFragment)
            }
        }, object : RowClickListener {
            override fun rowClickListener() {
                navigate(R.id.action_eventsFragment_to_eventDetailFragment2)
            }

        })
        moreAdapter = EventAdapter(object : FavouriteChecker {
            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
                favouriteEvent(application.auth.isGuest,
                    checkbox,
                    isFav,
                    R.id.action_eventsFragment_to_postLoginFragment)
            }
        }, object : RowClickListener {
            override fun rowClickListener() {
                navigate(R.id.action_eventsFragment_to_eventDetailFragment2)
            }
        })
        eventAdapter = EventAdapter(object : FavouriteChecker {
            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
                favouriteEvent(application.auth.isGuest,
                    checkbox,
                    isFav,
                    R.id.action_eventsFragment_to_postLoginFragment)
            }
        }, object : RowClickListener {
            override fun rowClickListener() {
                navigate(R.id.action_eventsFragment_to_eventDetailFragment2)
            }
        })
        binding!!.rvEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = featureAdapter

        }
        featureAdapter.events = createEventItems()

        binding!!.rvMoreEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moreAdapter
        }


        binding!!.rvNearEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapter

        }

    }

    private fun setupToolbarWithSearchItems() {
        binding!!.root.apply {
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
            binding!!.root.cardivewRTL?.shapeAppearanceModel =
                binding!!.root.cardivewRTL!!.shapeAppearanceModel
                    .toBuilder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .setTopRightCornerSize(radius)
                    .build()
        } else {
            binding!!.root.cardivewRTL?.shapeAppearanceModel =
                binding!!.root.cardivewRTL!!.shapeAppearanceModel
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
            locationHelper.locationSetUp(
                object : LocationHelper.LocationLatLng {
                    override fun getCurrentLocation(location: Location) {
                        Timber.e("Current Location ${location.latitude}")
                    }
                },
                object : LocationHelper.LocationCallBack {
                    override fun getLocationResultCallback(locationResult: LocationResult?) {
                        Timber.e("LocationResult ${locationResult!!.lastLocation.latitude}")
                    }

                }, activity)
        }
    }

    private fun updateGpsCheckUI(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                binding.apply {
                    tvViewMap.visibility = View.VISIBLE
                    rvNearEvent.visibility = View.VISIBLE
                    root.cardivewRTL.visibility = View.GONE
                }

            }
            is GpsStatus.Disabled -> {
                binding.apply {
                    tvViewMap.visibility = View.GONE
                    rvNearEvent.visibility = View.GONE
                    root.cardivewRTL.visibility = View.VISIBLE
                }

//                eventViewModel.showAlert(message = resources.getString(R.string.please_enable_gps))
            }
        }

    }

    private fun callingObservables() {
        lifecycleScope.launch {
            eventViewModel.getEventHomeToScreen(getCurrentLanguage().language)
        }
    }

    private fun subscribeToObservables() {
        eventViewModel.eventCategoryList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        moreAdapter.events = eventViewModel.getMoreEvents(it.value.events!!)
                        eventAdapter.events =
                            sortNearEvent(eventViewModel.getNearEvents(it.value.events!!))
                        if (!it.value.featureEvents.isNullOrEmpty()) {
                            featureAdapter.events = it.value.featureEvents!!
                        }
                    }
                }
                is Result.Failure -> {
                    showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)

                }
            }
        }
    }


    fun sortNearEvent(list: List<Events>): List<Events> {
        val myList = ArrayList<Events>()
        list.forEach {
            val distance = locationHelper.distance(LAT,
                LNG,
                it.longitude!!.toDouble(),
                it.latitude!!.toDouble())
            val km = distance / 0.62137
            val distanceKM: Double = String.format("%.1f", km).toDouble()
            it.distance = distanceKM
            myList.sortWith(compareBy({ it.distance }))
            nearEventList.add(it)
            myList.add(it)
        }
        return myList
    }
}