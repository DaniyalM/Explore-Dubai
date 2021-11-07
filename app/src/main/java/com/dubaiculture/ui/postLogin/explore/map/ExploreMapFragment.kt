package com.dubaiculture.ui.postLogin.explore.map

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.explore.local.models.ExploreMap
import com.dubaiculture.databinding.FragmentExploreMapBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.events.`interface`.DirectionClickListener
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.ui.postLogin.explore.adapters.SingleSelectionAdapter
import com.dubaiculture.ui.postLogin.explore.map.adapter.ExploreMapAdapter
import com.dubaiculture.ui.postLogin.explore.viewmodel.ExploreMapViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.Categories.ART_GALLERY
import com.dubaiculture.utils.Constants.Categories.FESTIVALS
import com.dubaiculture.utils.Constants.Categories.HERITAGE_SITES
import com.dubaiculture.utils.Constants.Categories.LIBRARIES
import com.dubaiculture.utils.Constants.NavBundles.CATEGORY
import com.dubaiculture.utils.Constants.NavBundles.LOCATION_LAT
import com.dubaiculture.utils.Constants.NavBundles.LOCATION_LNG
import com.dubaiculture.utils.handleApiError
import com.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ExploreMapFragment : BaseFragment<FragmentExploreMapBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    private val exploreMapViewModel: ExploreMapViewModel by viewModels()
    private var attractionCategoryList = ArrayList<AttractionCategory>()
    private var attractions = ArrayList<Attractions>()
    private var exploreMapList = ArrayList<ExploreMap>()
    private var eventList = ArrayList<Events>()
    private lateinit var mAdapter: SingleSelectionAdapter
    private var category: String = ""
    private var lat: Double? = null
    private var lng: Double? = null
    val nearEvent = R.drawable.events_map
    val farEvent = R.drawable.events_away
    val nearAttractions = R.drawable.attraction_close
    val farAttractions = R.drawable.attraction_away
    val nearHeritage = R.drawable.heritage_inrange
    val farHeritage = R.drawable.heritage_outrange
    val nearFestival = R.drawable.festival_inrange
    val farFestival = R.drawable.festival_outrange
    val nearLibrary = R.drawable.library_inrange
    val farLibrary = R.drawable.library_outrange
    lateinit var exploreNearAdapter: ExploreMapAdapter

    @Inject
    lateinit var locationHelper: LocationHelper

    @Inject
    lateinit var glide: RequestManager
    lateinit var mapView: MapView
    var googleMap: GoogleMap? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreMapBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(exploreMapViewModel)
        arguments?.apply {
            lat = getDouble(LOCATION_LAT)
            lng = getDouble(LOCATION_LNG)
        }
        backArrowRTL(binding.header.back)
        appendInAttractionCategoryList()
//        lifecycleScope.launch {
//        }
        binding.header.back.setOnClickListener(this)
        binding.ImgChangeView.setOnClickListener(this)
        mapSetUp()
        callingObserver()
        mapView = MapView(activity)
    }


    private fun setupRecyclerView() {
        mAdapter = SingleSelectionAdapter(requireContext(),
            object : SingleSelectionAdapter.InvokeListener {
                override fun getRowClick(position: Int) {
                    filter(position)
                }
            })

        binding.rvMapHeader.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = mAdapter
            mAdapter.attractions = attractionCategoryList
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                back()
            }
            R.id.ImgChangeView -> {
                val bundle = Bundle()
                bundle.putParcelableArrayList(
                    Constants.NavBundles.EXPLORE_MAP_LIST,
                    exploreMapList as java.util.ArrayList<out Parcelable>
                )
                bundle.putString(CATEGORY, category)
                lat?.let {
                    bundle.putDouble(LOCATION_LAT, it)
                }
                lng?.let {
                    bundle.putDouble(LOCATION_LNG, it)
                }

                navigate(R.id.action_exploreMapFragment_to_exploreBottomSheetFragment, bundle)
            }
        }
    }

    private fun callingObserver() {
        exploreMapViewModel.exploreAttractionsEvents.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.value.attractionCategory!!.forEach {
                        attractionCategoryList.add(it)
                        it.attractions!!.map {
                            attractions.add(it)
                        }
                    }


                    it.value.events!!.forEach {
                        eventList.add(it)
                    }
                    setupRecyclerView()
                    rvSetUp(
                        exploreMapViewModel.mergeArrayList(
                            exploreMapList,
                            attractions,
                            eventList,
                            locationHelper, lat, lng
                        )
                    )
                    googleMap?.apply {
                        exploreMapViewModel.pinsOnMap(
                            exploreMapViewModel.mergeArrayList(
                                exploreMapList,
                                attractions,
                                eventList,
                                locationHelper, lat, lng
                            ),
                            this,
                            inRangeIcon = nearAttractions,
                            outRangeIcon = farAttractions,
                            inRangeEventIcon = nearEvent,
                            outRangeEventIcon = farEvent,
                        )
                        currentLocation(this)
                        radiusOnMap(this)
                    }

                }
                is Result.Failure -> {
                    handleApiError(it, exploreMapViewModel)
                }
            }
        }
    }

    private fun appendInAttractionCategoryList() {
        val all = AttractionCategory(
            id = "1",
            title = getString(R.string.all),
            icon = "e",
            color = "",
        )
        val events = AttractionCategory(
            id = "2",
            title = getString(R.string.events),
            icon = "",
            color = "",
        )
        attractionCategoryList.add(all)
        attractionCategoryList.add(events)
    }


    private fun mapSetUp() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)
    }

    // filter a/c to the design position  0 -> All , 1 -> Event 2-> museum etc pins on map loaded setup of Rv call
    private fun filter(position: Int) {


        when (position) {
            0 -> {

                category = resources.getString(R.string.all)
                googleMap?.apply {

                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.mergeArrayList(
                            exploreMapList,
                            attractions,
                            eventList,
                            locationHelper, lat, lng
                        ),
                        this,
                        inRangeIcon = nearAttractions,
                        outRangeIcon = farAttractions,
                        inRangeEventIcon = nearEvent,
                        outRangeEventIcon = farEvent,

                        )
                    rvSetUp(
                        exploreMapViewModel.mergeArrayList(
                            exploreMapList,
                            attractions,
                            eventList,
                            locationHelper, lat, lng
                        )
                    )
                }
            }
            1 -> {
                category = resources.getString(R.string.events)
                googleMap?.apply {
                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.eventFilter(
                            locationHelper,
                            exploreMapList,
                            eventList, lat, lng
                        ),
                        this,
                        inRangeEventIcon = nearEvent,
                        outRangeEventIcon = farEvent,
                    )
                    rvSetUp(
                        exploreMapViewModel.eventFilter(
                            locationHelper,
                            exploreMapList,
                            eventList,
                            lat,
                            lng
                        )
                    )
                }

            }
            2 -> {
                category = resources.getString(R.string.museum)
                googleMap?.apply {

                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.attractionFilter(
                            category,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        ), this,
                        inRangeEventIcon = 0,
                        outRangeEventIcon = 0,
                        inRangeIcon = nearAttractions,
                        outRangeIcon = farAttractions
                    )
                    rvSetUp(
                        exploreMapViewModel.attractionFilter(
                            category,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        )
                    )
                }
            }
            3 -> {
                category = resources.getString(R.string.heritage)
                googleMap?.apply {
                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.attractionFilter(
                            HERITAGE_SITES,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        ), this,
                        inRangeIcon = nearHeritage,
                        outRangeIcon = farHeritage
                    )
                    rvSetUp(
                        exploreMapViewModel.attractionFilter(
                            HERITAGE_SITES,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        )
                    )
                }

            }
            4 -> {
                category = resources.getString(R.string.art_gallery)
                googleMap?.apply {
                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.attractionFilter(
                            ART_GALLERY,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        ), this,
                        inRangeIcon = nearAttractions,
                        outRangeIcon = farAttractions
                    )
                    rvSetUp(
                        exploreMapViewModel.attractionFilter(
                            ART_GALLERY,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        )
                    )

                }


            }
            5 -> {
                category = resources.getString(R.string.festivals)
                googleMap?.apply {
                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.attractionFilter(
                            FESTIVALS,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        ), this,
                        inRangeIcon = nearFestival,
                        outRangeIcon = farFestival
                    )
                    rvSetUp(
                        exploreMapViewModel.attractionFilter(
                            FESTIVALS,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        )
                    )
                }

            }
            6 -> {
                category = resources.getString(R.string.libraries)
                googleMap?.apply {
                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.attractionFilter(
                            LIBRARIES,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        ), this,
                        inRangeIcon = nearLibrary,
                        outRangeIcon = farLibrary
                    )
                    rvSetUp(
                        exploreMapViewModel.attractionFilter(
                            LIBRARIES,
                            locationHelper,
                            exploreMapList,
                            attractions, lat, lng
                        )
                    )
                }


            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
    }

    // rv setUp get the sorted by distance and a/c to the top header categories List e.g if press on heritage so it sorted by distance and all heritage items
    private fun rvSetUp(list: List<ExploreMap>) {
        exploreNearAdapter = ExploreMapAdapter(isArabic(), object : RowClickListener {
            override fun rowClickListener(position: Int) {
            }

            override fun rowClickListener(position: Int, imageView: ImageView) {

            }
        }, object : DirectionClickListener {
            override fun directionClickListener(position: Int) {
                if (!attractions[position].latitude.isNullOrEmpty()) {
                    // open google map application
                    navigateToGoogleMap(
                        lat.toString(),
                        lng.toString(),
                        attractions[position].latitude.toString(),
                        attractions[position].longitude.toString()
                    )
                }
            }


        })
        binding.rvExploreMap.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = exploreNearAdapter
        }
        exploreNearAdapter.explore = list
        exploreNearAdapter.notifyDataSetChanged()
        currentLocation(googleMap)
        radiusOnMap(googleMap)
    }

    // show radius of map 5 Km meter
    private fun radiusOnMap(googleMap: GoogleMap?) {
        googleMap?.addCircle(
            CircleOptions()
                .fillColor(getColorWithAlpha(Color.CYAN, 0.15f))
                .strokeColor(getColorWithAlpha(Color.CYAN, 0.15f))
                .center(LatLng(lat ?: 24.8623, lng ?: 67.0627))
                .radius(5000.0)
                .strokeWidth(1f)
        )
    }

    // show current location on map
    private fun currentLocation(googleMap: GoogleMap?) {
        val trafficDigitalLatLng = LatLng(lat ?: 24.8623, lng ?: 67.0627)
        googleMap!!.addMarker(
            MarkerOptions()
                .position(trafficDigitalLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_current))
        )
            .title = "Current Location"
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                trafficDigitalLatLng, 12.0f
            )
        )
        googleMap.cameraPosition.target
    }


}

