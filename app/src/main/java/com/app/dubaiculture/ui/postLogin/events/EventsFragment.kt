package com.app.dubaiculture.ui.postLogin.events

import android.Manifest
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.EventItemsBinding
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.EVENT_MAP_LIST
import com.app.dubaiculture.utils.GpsStatus
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.app.neomads.utils.location.LocationUtils.enableLocationFromSettings
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
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

    var lat : Double ? = null
    var lng : Double ? = null

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            updateGpsCheckUI(status)
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            lat = locationResult.lastLocation.latitude
            lng = locationResult.lastLocation.longitude
            Timber.e("onLocationResult ${locationResult.lastLocation.latitude}")
            eventViewModel.getEventHomeToScreen(getCurrentLanguage().language)

        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventsBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(eventViewModel)
        locationPermission()
        cardViewRTL()
        setupToolbarWithSearchItems()
        subscribeToGpsListener()
        subscribeToObservables()
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            eventViewModel.getEventHomeToScreen(getCurrentLanguage().language)
        }
        binding.tvViewMap.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList(EVENT_MAP_LIST,nearList  as ArrayList<out Parcelable >)
            navigate(R.id.action_eventsFragment_to_eventNearMapFragment2,bundle)
        }
        binding.root.view_all_events.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventFilterFragment)
        }
        binding.root.search.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventFilterFragment)
        }
        binding.root.cardivewRTL.setOnClickListener {
            enableLocationFromSettings(requireActivity())
        }
    }

    private fun subscribeToGpsListener() = eventViewModel.gpsStatusLiveData
        .observe(viewLifecycleOwner, gpsObserver)

    private fun rvSetUp() {
        mAdapterNear = GroupAdapter()
        mAdapterMore = GroupAdapter()

        // features event RV
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
                        lat = location.latitude
                        lng = location.longitude
                        Timber.e("Current Location ${location.latitude}")
                    }
                },
                activity, locationCallback)
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
            }
        }
    }


    private fun subscribeToObservables() {
        eventViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_home)
                    }
                }
                is Result.Failure -> handleApiError(it, eventViewModel)
            }
        }
        eventViewModel.eventCategoryList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (nearList.isNullOrEmpty()) {
                        binding.tvViewMap.visibility = View.GONE
                    }
                    binding.tvEventTitle.visibility = View.VISIBLE
                    binding.tvNearEventTitle.visibility = View.VISIBLE
                    binding.tvViewMap.visibility = View.VISIBLE
                    binding.viewAllEvents.visibility = View.VISIBLE
                    binding.tvMoreEventTitle.visibility = View.VISIBLE
                    it.let {
                        rvSetUp()

                        if (mAdapterMore.itemCount>0){
                            mAdapterMore.clear()
                        }
                        if (mAdapterNear.itemCount>0){
                            mAdapterNear.clear()
                        }
                        if (groupAdapter.itemCount>0){
                            groupAdapter.clear()
                        }

                        it.value.events!!.forEach {
                            moreList.add(it)
                            mAdapterMore.add(EventListItem<EventItemsBinding>(object :
                                FavouriteChecker {
                                override fun checkFavListener(
                                    checkbox: CheckBox,
                                    pos: Int,
                                    isFav: Boolean,
                                    itemId: String,
                                ) {
                                    favouriteClick(
                                        checkbox,
                                        isFav,
                                        R.id.action_eventsFragment_to_postLoginFragment,
                                        itemId, eventViewModel
                                    )
                                }

                            }, object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    val eventObj = moreList[position]
                                    val bundle = Bundle()
                                    bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                        eventObj)
                                    navigate(R.id.action_eventsFragment_to_eventDetailFragment2,
                                        bundle)
                                }

                            }, event = it, resLayout = R.layout.event_items,activity))
                        }
//                        sortNearEvent(it.value.events!!)

                        sortNearEvent(it.value.events!!).forEach {
                            mAdapterNear.add(EventListItem<EventItemsBinding>(
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
                                            R.id.action_eventsFragment_to_postLoginFragment,
                                            itemId, eventViewModel
                                        )
                                    }

                                }, object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        val eventObj = nearList[position]
                                        val bundle = Bundle()
                                        bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                            eventObj)
                                        navigate(R.id.action_eventsFragment_to_eventDetailFragment2,
                                            bundle)
                                    }

                                }, event = it, resLayout = R.layout.event_items,activity))
                        }



                        if (!it.value.featureEvents.isNullOrEmpty()) {
//                            featureAdapter.events =
                            it.value.featureEvents!!.forEach {
                                featureList.add(it)
                                groupAdapter.add(EventListItem<EventItemsBinding>(object :
                                    FavouriteChecker {
                                    override fun checkFavListener(
                                        checkbox: CheckBox,
                                        pos: Int,
                                        isFav: Boolean,
                                        itemId: String,
                                    ) {
                                        favouriteClick(
                                            checkbox,
                                            isFav,
                                            R.id.action_eventsFragment_to_postLoginFragment,
                                            itemId, eventViewModel
                                        )
                                    }

                                }, object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        val eventObj = featureList[position]
                                        val bundle = Bundle()
                                        bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                            eventObj)
                                        navigate(R.id.action_eventsFragment_to_eventDetailFragment2,
                                            bundle)
                                    }

                                }, event = it, resLayout = R.layout.event_items,activity))
                            }
                        }else{
                            binding.tvEventTitle.visibility = View.GONE
                        }

                    }
                }
                is Result.Failure -> {
                    binding.tvEventTitle.visibility = View.GONE
                    binding.tvNearEventTitle.visibility = View.GONE
                    binding.tvViewMap.visibility = View.GONE
                    binding.viewAllEvents.visibility = View.GONE
                    binding.tvMoreEventTitle.visibility = View.GONE
                    showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)

                }
            }
        }
    }


    fun sortNearEvent(list: List<Events>): List<Events> {
        val myList = ArrayList<Events>()
        nearList.clear()
        val sortedList = ArrayList<Events>()
            list.forEach {
                val distance = locationHelper.distance(lat?: 24.8623,
                    lng?:67.0627,
                    it.latitude.toString().ifEmpty { "24.83250180519734" }.toDouble(),
                    it.longitude.toString().ifEmpty { "67.08119661055807" }.toDouble())
                it.distance = distance
                it.currentLat = lat?:24.8623
                it.currentLng = lng?:67.0627
                myList.add(it)

            }
                myList.sortWith(compareBy(Events::distance))
                myList.map {
                    sortedList.add(it)
                    nearList.add(it)
        }
        return sortedList
    }
}