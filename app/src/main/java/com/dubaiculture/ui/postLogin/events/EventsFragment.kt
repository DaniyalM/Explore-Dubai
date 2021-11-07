package com.dubaiculture.ui.postLogin.events

//import com.app.neomads.utils.location.LocationUtils.enableLocationFromSettings

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.databinding.EventItemsBinding
import com.dubaiculture.databinding.FragmentEventsBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.NavBundles.EVENT_FILTER
import com.dubaiculture.utils.Constants.NavBundles.EVENT_MAP_LIST
import com.dubaiculture.utils.Constants.NavBundles.EVENT_OBJECT
import com.dubaiculture.utils.GpsStatus
import com.dubaiculture.utils.enableLocationFromSettings
import com.dubaiculture.utils.handleApiError
import com.dubaiculture.utils.location.LocationHelper
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
    var eventListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    var backflagNavigation = false
    private var events: Events? = null
    private var eventFilterFlag = false


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            if (it.getParcelable<Events>(EVENT_OBJECT) != null) {
                events = it.getParcelable(EVENT_OBJECT)
            }
            if (it.getBoolean(EVENT_FILTER)) {
                eventFilterFlag = true
            }
        }
    }

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

    var lat: Double? = null
    var lng: Double? = null

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            updateGpsCheckUI(status)
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventsBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (backflagNavigation) {
            backflagNavigation = false
            events = null
            eventFilterFlag=false
            navigateBack()
        }

        if (events != null) {
            backflagNavigation = true
            navigate(R.id.action_eventsFragment_to_eventDetailFragment2, Bundle().apply {
                putParcelable(
                    EVENT_OBJECT,
                    events
                )
            })
        }
        if (eventFilterFlag) {
            backflagNavigation = true
            navigate(R.id.action_eventsFragment_to_eventFilterFragment)
        }

        subscribeUiEvents(eventViewModel)
        locationPermission()
        cardViewRTL()
        setupToolbarWithSearchItems()
        subscribeToGpsListener()
        subscribeToObservables()
        if (!this::mAdapterMore.isInitialized) {
            rvSetUp()
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            eventViewModel.getEventHomeToScreen(getCurrentLanguage().language)
        }
        binding.tvViewMap.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList(EVENT_MAP_LIST, nearList as ArrayList<out Parcelable>)
            navigate(R.id.action_eventsFragment_to_eventNearMapFragment2, bundle)
        }
        binding.viewAllEvents.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventFilterFragment)
        }
        binding.toolbarSnippet.toolbarLayout.search.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventFilterFragment)
        }
        binding.planATripLayout.cardivewRTL.setOnClickListener {
            activity.enableLocationFromSettings()
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
            adapter = eventListAdapter

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
        binding.toolbarSnippet.toolbarLayout.apply {
            profilePic.visibility = View.GONE
            imgDrawer.visibility = View.GONE
            toolbarTitle.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.events)
            }
        }
    }

    private fun cardViewRTL() {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        binding.planATripLayout.apply {
            animationViewEvent.visibility = View.VISIBLE
            animationView.visibility = View.GONE
            if (isArabic()) {
                cardivewRTL.shapeAppearanceModel =
                    cardivewRTL.shapeAppearanceModel
                        .toBuilder()
                        .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                        .setTopRightCornerSize(radius)
                        .build()
            } else {
                cardivewRTL.shapeAppearanceModel =
                    cardivewRTL.shapeAppearanceModel
                        .toBuilder()
                        .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                        .setBottomRightCornerSize(radius)
                        .build()
            }
        }
    }

    private fun locationPermission() {
        try {
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
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            lat = locationResult.lastLocation.latitude
                            lng = locationResult.lastLocation.longitude
                            eventViewModel.getEventHomeToScreen(getCurrentLanguage().language)
                        }
                    }
                )
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }


    }

    private fun updateGpsCheckUI(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                binding.apply {
                    tvViewMap.visibility = View.VISIBLE
                    rvNearEvent.visibility = View.VISIBLE
                    planATripLayout.cardivewRTL.visibility = View.GONE
                }

            }
            is GpsStatus.Disabled -> {
                binding.apply {
                    tvViewMap.visibility = View.GONE
                    rvNearEvent.visibility = View.GONE
                    planATripLayout.cardivewRTL.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun subscribeToObservables() {
        eventViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_home)
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


                        if (mAdapterMore.itemCount > 0) {
                            mAdapterMore.clear()
                        }
                        if (mAdapterNear.itemCount > 0) {
                            mAdapterNear.clear()
                        }
                        if (eventListAdapter.itemCount > 0) {
                            eventListAdapter.clear()
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
                                    navigateByDirections(
                                        EventsFragmentDirections.actionEventsFragmentToEventDetailFragment2(
                                            eventObj.id!!
                                        )
                                    )
//                                    val bundle = Bundle()
//                                    bundle.putParcelable(
//                                        EVENT_OBJECT,
//                                        eventObj
//                                    )
//                                    navigate(
//                                        R.id.action_eventsFragment_to_eventDetailFragment2,
//                                        bundle
//                                    )
                                }

                                override fun rowClickListener(position: Int, imageView: ImageView) {

                                }

                            },object : EventListItem.SurveySubmitListener{
                                override fun submitBtnClickListener(position: Int) {
                                }

                            },


                                event = it, resLayout = R.layout.event_items, activity))
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
                                        navigateByDirections(
                                            EventsFragmentDirections.actionEventsFragmentToEventDetailFragment2(
                                                eventObj.id!!
                                            )
                                        )
//                                        val bundle = Bundle()
//                                        bundle.putParcelable(
//                                            EVENT_OBJECT,
//                                            eventObj
//                                        )
//                                        navigate(
//                                            R.id.action_eventsFragment_to_eventDetailFragment2,
//                                            bundle
//                                        )
                                    }

                                    override fun rowClickListener(
                                        position: Int,
                                        imageView: ImageView
                                    ) {

                                    }

                                },object : EventListItem.SurveySubmitListener{
                                    override fun submitBtnClickListener(position: Int) {
                                    }

                                }, event = it, resLayout = R.layout.event_items, activity
                            )
                            )
                        }



                        if (!it.value.featureEvents.isNullOrEmpty()) {
//                            featureAdapter.events =
                            it.value.featureEvents!!.forEach {
                                featureList.add(it)
                                eventListAdapter.add(EventListItem<EventItemsBinding>(object :
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
                                        navigateByDirections(
                                            EventsFragmentDirections.actionEventsFragmentToEventDetailFragment2(
                                                eventObj.id!!
                                            )
                                        )
//                                        val bundle = Bundle()
//                                        bundle.putParcelable(
//                                            EVENT_OBJECT,
//                                            eventObj
//                                        )
//                                        navigate(
//                                            R.id.action_eventsFragment_to_eventDetailFragment2,
//                                            bundle
//                                        )
                                    }

                                    override fun rowClickListener(
                                        position: Int,
                                        imageView: ImageView
                                    ) {

                                    }

                                },object : EventListItem.SurveySubmitListener{
                                    override fun submitBtnClickListener(position: Int) {
                                    }

                                }, event = it, resLayout = R.layout.event_items, activity))
                            }
                        } else {
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
            val longitude = if (it.longitude.isEmpty() || it.longitude == "null") "67.08119661055807" else it.longitude
            val latitude = if (it.latitude.isEmpty()|| it.longitude == "null") "24.83250180519734" else it.latitude
            val distance = locationHelper.distance(
                lat ?: 24.8623,
                lng ?: 67.0627,
                longitude.toDouble(),
                latitude.toDouble()
            )
            it.distance = distance
            it.currentLat = lat ?: 24.8623
            it.currentLng = lng ?: 67.0627
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