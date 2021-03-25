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
import com.app.dubaiculture.databinding.EventItemsBinding
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventAdapter
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_events.view.*
import kotlinx.android.synthetic.main.plan_a_trip_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>() {
    private val eventViewModel: EventViewModel by viewModels()
    lateinit var mAdapterNear: GroupAdapter<GroupieViewHolder>
    lateinit var mAdapterMore: GroupAdapter<GroupieViewHolder>
    private val featureList = mutableListOf<Events>()
    private val moreList = mutableListOf<Events>()
    private val nearList = mutableListOf<Events>()

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

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            updateGpsCheckUI(status)
        }
    }

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
        binding.tvViewMap.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventNearMapFragment2)
        }
        binding.root.view_all_events.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventFilterFragment)
        }
    }

    private fun subscribeToGpsListener() = eventViewModel.gpsStatusLiveData
        .observe(viewLifecycleOwner, gpsObserver)

    private fun rvSetUp() {
        mAdapterNear = GroupAdapter()
        mAdapterMore = GroupAdapter()

        binding.rvEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter

        }

        binding.rvMoreEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapterMore
        }


        binding.rvNearEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapterNear

        }

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
        binding.root.apply {
            if (isArabic()) {
                cardivewRTL?.shapeAppearanceModel =
                    cardivewRTL!!.shapeAppearanceModel
                        .toBuilder()
                        .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                        .setTopRightCornerSize(radius)
                        .build()
            } else {
                cardivewRTL?.shapeAppearanceModel =
                    cardivewRTL!!.shapeAppearanceModel
                        .toBuilder()
                        .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                        .setBottomRightCornerSize(radius)
                        .build()
            }
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
                        it.value.events!!.forEach {
                            groupAdapter.add(EventListItem<EventItemsBinding>(object :
                                FavouriteChecker {
                                override fun checkFavListener(
                                    checkbox: CheckBox,
                                    pos: Int,
                                    isFav: Boolean,
                                ) {
                                    favouriteEvent(application.auth.isGuest,
                                        checkbox,
                                        isFav,
                                        R.id.action_eventsFragment_to_postLoginFragment)
                                }
                            }, object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    val eventObj = featureList[position]
                                    val bundle = Bundle()
                                    bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                        eventObj)
                                    navigate(R.id.action_eventsFragment_to_eventDetailFragment2,bundle)
                                }

                            }, event = it, resLayout = R.layout.event_items))
                        }
                        sortNearEvent(eventViewModel.getNearEvents(it.value.events!!)).forEach {
                            mAdapterNear.add(EventListItem<EventItemsBinding>(
                                object : FavouriteChecker {
                                    override fun checkFavListener(
                                        checkbox: CheckBox,
                                        pos: Int,
                                        isFav: Boolean,
                                    ) {
                                        favouriteEvent(application.auth.isGuest,
                                            checkbox,
                                            isFav,
                                            R.id.action_eventsFragment_to_postLoginFragment)
                                    }
                                }, object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        val eventObj = nearList[position]
                                        val bundle = Bundle()
                                        bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                            eventObj)
                                        navigate(R.id.action_eventsFragment_to_eventDetailFragment2,bundle)
                                    }

                                }, event = it, resLayout = R.layout.event_items))
                        }

                        if (!it.value.featureEvents.isNullOrEmpty()) {
//                            featureAdapter.events =
                            it.value.featureEvents!!.forEach {
                                featureList.add(it)
                                mAdapterMore.add(EventListItem<EventItemsBinding>(object :
                                    FavouriteChecker {
                                    override fun checkFavListener(
                                        checkbox: CheckBox,
                                        pos: Int,
                                        isFav: Boolean,
                                    ) {
                                        favouriteEvent(application.auth.isGuest,
                                            checkbox,
                                            isFav,
                                            R.id.action_eventsFragment_to_postLoginFragment)
                                    }
                                }, object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        val eventObj = featureList[position]
                                        val bundle = Bundle()
                                        bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                            eventObj)
                                        navigate(R.id.action_eventsFragment_to_eventDetailFragment2,bundle)
                                    }

                                }, event = it, resLayout = R.layout.event_items))
                            }
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
                it.latitude!!.toDouble(),
                it.longitude!!.toDouble())
            it.distance = distance
            myList.sortWith(compareBy({ it.distance }))
            nearEventList.add(it)
            myList.add(it)
        }
        return myList
    }
}