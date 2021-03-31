package com.app.dubaiculture.ui.postLogin.explore.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.app.dubaiculture.ui.postLogin.events.EventsFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.SingleSelectionAdapter
import com.app.dubaiculture.ui.postLogin.explore.map.adapter.ExploreMapAdapter
import com.app.dubaiculture.ui.postLogin.explore.viewmodel.ExploreMapViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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
    var mAdapter: SingleSelectionAdapter? = null
    lateinit var exploreNearAdapter: ExploreMapAdapter
    @Inject
    lateinit var locationHelper: LocationHelper
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
        mapSetUp()
        callingObserver()
    }

    private fun setupRecyclerView() {
        mAdapter = SingleSelectionAdapter(requireContext(),
            attractionCategoryList,
            object : SingleSelectionAdapter.InvokeListener {
                override fun getRowClick(position: Int) {
                    eventList.clear()
                    attractions.clear()
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
            icon = "",
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

    override fun onMapReady(p0: GoogleMap?) {

    }

    private fun filter(position : Int){

        when(position){
            0->{}
            1->{
                rvSetUp(eventFilter())
            }
            2->{
                rvSetUp(attractionFilter())
            }
        }
    }

    private fun rvSetUp(list:List<ExploreMap>) {
        exploreNearAdapter = ExploreMapAdapter()
        binding.rvExploreMap.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = exploreNearAdapter
        }
        exploreNearAdapter.explore = list
    }
//    private fun flatMap(){
//        val combinedList: List<String> = Stream.of(listOne, listTwo)
//            .flatMap { x -> x.stream() }
//            .collect(Collectors.toList())
//    }\

    fun sortNearEvent(list: List<Events>): List<Events> {
        val myList = ArrayList<Events>()
        list.forEach {
            val distance = locationHelper.distance(Constants.StaticLatLng.LAT,
                Constants.StaticLatLng.LNG,
                it.latitude!!.toDouble(),
                it.longitude!!.toDouble())
            it.distance = distance
            myList.sortWith(compareBy({ it.distance }))
            myList.add(it)
        }
        return myList
    }

    fun sortNearAttraction(list: List<Attractions>): List<Attractions> {
        val myList = ArrayList<Attractions>()
        list.forEach {
            val distance = locationHelper.distance(Constants.StaticLatLng.LAT,
                Constants.StaticLatLng.LNG,
                ((it.latitude.toString().ifEmpty { "24.83250180519734" }).toDouble()),
                (it.longitude.toString().ifEmpty {"67.08119661055807"}).toDouble())
            it.distance = distance
            myList.sortWith(compareBy({ it.distance }))
            myList.add(it)
        }
        return myList
    }
    fun attractionFilter():List<ExploreMap>{
     val list =   sortNearAttraction(attractions).filter {
            it.category.trim() == "Museums"
        }
        list.forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude,
                    lng = it.longitude,
                    pin = ""
                )
            )
        }

        return exploreMapList
    }

    fun eventFilter():List<ExploreMap> {
        sortNearEvent(eventList).forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude,
                    lng = it.longitude,
                    pin = ""
                )
            )
        }
        return exploreMapList
    }

}