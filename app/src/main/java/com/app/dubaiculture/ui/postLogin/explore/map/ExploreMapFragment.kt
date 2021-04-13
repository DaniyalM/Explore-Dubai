package com.app.dubaiculture.ui.postLogin.explore.map

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
import com.app.dubaiculture.utils.Constants.Categories.MUSEUMS
import com.app.dubaiculture.utils.Constants.NavBundles.CATEGORY
import com.app.dubaiculture.utils.Constants.StaticLatLng.LAT
import com.app.dubaiculture.utils.Constants.StaticLatLng.LNG
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_back.view.*
import kotlinx.coroutines.launch
import java.security.AccessController.checkPermission
import javax.inject.Inject


@AndroidEntryPoint
class ExploreMapFragment : BaseFragment<FragmentExploreMapBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    private val exploreMapViewModel: ExploreMapViewModel by viewModels()
    private var attractionCategoryList = ArrayList<AttractionCategory>()
    private var attractions = ArrayList<Attractions>()
    private var exploreMapList = ArrayList<ExploreMap>()
    private var eventList = ArrayList<Events>()
    var mAdapter: SingleSelectionAdapter? = null
    var category  : String = ""
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(exploreMapViewModel)
        appendInAttractionCategoryList()
        lifecycleScope.launch {
            exploreMapViewModel.getExploreMap(getCurrentLanguage().language)
        }
        binding.root.back.setOnClickListener(this)
        binding.ImgChangeView.setOnClickListener (this)
        mapSetUp()
        callingObserver()
        mapView = MapView(context)
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
            R.id.ImgChangeView->{
                val bundle= Bundle()
                bundle.putParcelableArrayList(Constants.NavBundles.EXPLORE_MAP_LIST,
                    exploreMapList as java.util.ArrayList<out Parcelable>)
                bundle.putString(CATEGORY,category)
                navigate(R.id.action_exploreMapFragment_to_exploreBottomSheetFragment,bundle)
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
                    rvSetUp(exploreMapViewModel.mergeArrayList(exploreMapList,
                        attractions,
                        eventList,
                        locationHelper))
                    exploreMapViewModel.pinsOnMap(exploreMapViewModel.mergeArrayList(exploreMapList,
                        attractions,
                        eventList,
                        locationHelper), googleMap)
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
                exploreMapViewModel.pinsOnMap(exploreMapViewModel.mergeArrayList(exploreMapList, attractions, eventList, locationHelper), googleMap)
                rvSetUp(exploreMapViewModel.mergeArrayList(exploreMapList, attractions, eventList, locationHelper))
            }
            1 -> {
                category = resources.getString(R.string.events)

                exploreMapViewModel.pinsOnMap(exploreMapViewModel.eventFilter(locationHelper, exploreMapList, eventList), googleMap)
                rvSetUp(exploreMapViewModel.eventFilter(locationHelper, exploreMapList, eventList))
            }
            2 -> {
                category = resources.getString(R.string.museum)

                exploreMapViewModel.pinsOnMap(exploreMapViewModel.attractionFilter(MUSEUMS, locationHelper, exploreMapList, attractions), googleMap)
                rvSetUp(exploreMapViewModel.attractionFilter(MUSEUMS, locationHelper, exploreMapList, attractions))
            }
            3 -> {
                category = resources.getString(R.string.heritage)
                exploreMapViewModel.pinsOnMap(exploreMapViewModel.attractionFilter(HERITAGE_SITES, locationHelper, exploreMapList, attractions), googleMap)
                rvSetUp(exploreMapViewModel.attractionFilter(HERITAGE_SITES,
                    locationHelper,
                    exploreMapList,
                    attractions))
            }
            4 -> {
                category = resources.getString(R.string.art_gallery)
                exploreMapViewModel.pinsOnMap(exploreMapViewModel.attractionFilter(ART_GALLERY,
                    locationHelper,
                    exploreMapList,
                    attractions), googleMap)
                rvSetUp(exploreMapViewModel.attractionFilter(ART_GALLERY,
                    locationHelper,
                    exploreMapList,
                    attractions))


            }
            5 -> {
                category = resources.getString(R.string.festivals)
                exploreMapViewModel.pinsOnMap(exploreMapViewModel.attractionFilter(FESTIVALS, locationHelper, exploreMapList, attractions), googleMap)
                rvSetUp(exploreMapViewModel.attractionFilter(FESTIVALS,
                    locationHelper,
                    exploreMapList,
                    attractions))
            }
            6 -> {
                category = resources.getString(R.string.libraries)
                exploreMapViewModel.pinsOnMap(exploreMapViewModel.attractionFilter(LIBRARIES,
                    locationHelper,
                    exploreMapList,
                    attractions), googleMap)
                rvSetUp(exploreMapViewModel.attractionFilter(LIBRARIES,
                    locationHelper,
                    exploreMapList,
                    attractions))

            }
        }
    }
    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
    }

    private fun rvSetUp(list: List<ExploreMap>) {
        exploreNearAdapter = ExploreMapAdapter()
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
                .center(LatLng(24.8623, 67.0627))
                .radius(5000.0)
                .strokeWidth(1f)
                .strokeColor(ContextCompat.getColor(requireContext(), R.color.map_radius_color))
                .fillColor(0x220000FF)
//                .fillColor(ContextCompat.getColor(requireContext(), R.color.map_radius_color))
        )
    }
    private fun currentLocation(googleMap: GoogleMap?){
        val trafficDigitalLatLng = LatLng(24.8623, 67.0627)
        googleMap!!.addMarker(MarkerOptions()
            .position(trafficDigitalLatLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_current)))
            .title = "Current Location"
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                trafficDigitalLatLng, 12.0f
            )
        )
        googleMap.cameraPosition.target
    }
}