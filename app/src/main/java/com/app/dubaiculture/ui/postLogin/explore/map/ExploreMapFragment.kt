package com.app.dubaiculture.ui.postLogin.explore.map

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.exploremap.model.ExploreMap
import com.app.dubaiculture.databinding.FragmentExploreMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.SingleSelectionAdapter
import com.app.dubaiculture.ui.postLogin.explore.map.adapter.ExploreMapAdapter
import com.app.dubaiculture.ui.postLogin.explore.viewmodel.ExploreMapViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.Categories.ART_GALLERY
import com.app.dubaiculture.utils.Constants.Categories.FESTIVALS
import com.app.dubaiculture.utils.Constants.Categories.HERITAGE_SITES
import com.app.dubaiculture.utils.Constants.Categories.LIBRARIES
import com.app.dubaiculture.utils.Constants.NavBundles.CATEGORY
import com.app.dubaiculture.utils.Constants.NavBundles.LOCATION_LAT
import com.app.dubaiculture.utils.Constants.NavBundles.LOCATION_LNG
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_back.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ExploreMapFragment : BaseFragment<FragmentExploreMapBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    private val exploreMapViewModel: ExploreMapViewModel by viewModels()
    private var attractionCategoryList = ArrayList<AttractionCategory>()
    private var attractions = ArrayList<Attractions>()
    private var exploreMapList = ArrayList<ExploreMap>()
    private var eventList = ArrayList<Events>()
    private var mAdapter: SingleSelectionAdapter? = null
    private var category: String = ""
    private var lat: Double? = null
    private var lng: Double? = null

    lateinit var exploreNearAdapter: ExploreMapAdapter

    @Inject
    lateinit var locationHelper: LocationHelper

    @Inject
    lateinit var glide: RequestManager
    lateinit var mapView: MapView
    lateinit var googleMap: GoogleMap
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
        backArrowRTL(binding.root.back)
        appendInAttractionCategoryList()
        lifecycleScope.launch {
            exploreMapViewModel.getExploreMap(getCurrentLanguage().language)
        }
        binding.root.back.setOnClickListener(this)
        binding.ImgChangeView.setOnClickListener(this)
        mapSetUp()
        callingObserver()
        mapView = MapView(activity)
    }


    private fun setupRecyclerView() {
        mAdapter = SingleSelectionAdapter(requireContext(),
            attractionCategoryList,
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
                navigate(R.id.action_exploreMapFragment_to_exploreBottomSheetFragment, bundle)
            }
        }
    }

    private fun callingObserver() {
        exploreMapViewModel.exploreAttractionsEvents.observe(viewLifecycleOwner) {
            when (it) {
                is com.app.dubaiculture.data.Result.Success -> {
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
                    exploreMapViewModel.pinsOnMap(
                        exploreMapViewModel.mergeArrayList(
                            exploreMapList,
                            attractions,
                            eventList,
                            locationHelper, lat, lng
                        ), googleMap
                    )
                    currentLocation(googleMap)
                    setupMap(googleMap)
                }
                is com.app.dubaiculture.data.Result.Failure -> {
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

    private fun filter(position: Int) {
        when (position) {
            0 -> {
                category = resources.getString(R.string.all)
                exploreMapViewModel.pinsOnMap(
                    exploreMapViewModel.mergeArrayList(
                        exploreMapList,
                        attractions,
                        eventList,
                        locationHelper, lat, lng
                    ), googleMap
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
            1 -> {
                category = resources.getString(R.string.events)

                exploreMapViewModel.pinsOnMap(
                    exploreMapViewModel.eventFilter(
                        locationHelper,
                        exploreMapList,
                        eventList, lat, lng
                    ), googleMap
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
            2 -> {
                category = resources.getString(R.string.museum)

                exploreMapViewModel.pinsOnMap(
                    exploreMapViewModel.attractionFilter(
                        category,
                        locationHelper,
                        exploreMapList,
                        attractions, lat, lng
                    ), googleMap
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
            3 -> {
                category = resources.getString(R.string.heritage)
                exploreMapViewModel.pinsOnMap(
                    exploreMapViewModel.attractionFilter(
                        HERITAGE_SITES,
                        locationHelper,
                        exploreMapList,
                        attractions, lat, lng
                    ), googleMap
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
            4 -> {
                category = resources.getString(R.string.art_gallery)
                exploreMapViewModel.pinsOnMap(
                    exploreMapViewModel.attractionFilter(
                        ART_GALLERY,
                        locationHelper,
                        exploreMapList,
                        attractions, lat, lng
                    ), googleMap
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
            5 -> {
                category = resources.getString(R.string.festivals)
                exploreMapViewModel.pinsOnMap(
                    exploreMapViewModel.attractionFilter(
                        FESTIVALS,
                        locationHelper,
                        exploreMapList,
                        attractions, lat, lng
                    ), googleMap
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
            6 -> {
                category = resources.getString(R.string.libraries)
                exploreMapViewModel.pinsOnMap(
                    exploreMapViewModel.attractionFilter(
                        LIBRARIES,
                        locationHelper,
                        exploreMapList,
                        attractions, lat, lng
                    ), googleMap
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

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
    }

    private fun rvSetUp(list: List<ExploreMap>) {
        exploreNearAdapter = ExploreMapAdapter(isArabic())
        binding.rvExploreMap.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = exploreNearAdapter
        }
        exploreNearAdapter.explore = list
        exploreNearAdapter.notifyDataSetChanged()
        currentLocation(googleMap)
        setupMap(googleMap)
    }

    private fun setupMap(googleMap: GoogleMap?) {
        googleMap?.addCircle(
            CircleOptions()
                    .fillColor(getColorWithAlpha(Color.CYAN, 0.15f))
                    .strokeColor(getColorWithAlpha(Color.CYAN, 0.15f))
                .center(LatLng(lat ?: 24.8623, lng ?: 67.0627))
                .radius(5000.0)
                .strokeWidth(1f)
        )
    }

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