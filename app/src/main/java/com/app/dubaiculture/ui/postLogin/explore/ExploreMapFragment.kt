package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentExploreMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.HeaderModel
import com.app.dubaiculture.ui.postLogin.explore.adapters.SingleSelectionAdapter
import com.app.dubaiculture.ui.postLogin.explore.viewmodel.ExploreViewModel
import com.app.dubaiculture.utils.handleApiError
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_back.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreMapFragment : BaseFragment<FragmentExploreMapBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    private val exploreViewModel: ExploreViewModel by viewModels()
    private var attractionCategoryList = ArrayList<AttractionCategory>()
    private var eventList = ArrayList<Events>()


    var mAdapter: SingleSelectionAdapter? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreMapBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(exploreViewModel)
        appendInAttractionCategoryList()
        lifecycleScope.launch {
            exploreViewModel.getExploreMap(getCurrentLanguage().language)
        }
        binding.root.back.setOnClickListener(this)
        mapSetUp()
        callingObserver()


    }

    private fun setupRecyclerView() {
//        populateRV()
        mAdapter = SingleSelectionAdapter(requireContext(),
            attractionCategoryList,
            object : SingleSelectionAdapter.InvokeListener {
                override fun getRowClick(title: String) {
                    exploreViewModel.showToast(title)
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
        exploreViewModel.exploreAttractionsEvents.observe(viewLifecycleOwner) {
            when (it) {
                is com.app.dubaiculture.data.Result.Success -> {
                    it.value.attractionCategory!!.forEach {
                        attractionCategoryList.add(it)

                    }
                    eventList = it.value.events!!
                    setupRecyclerView()
                }
                is com.app.dubaiculture.data.Result.Failure -> {
                    handleApiError(it, exploreViewModel)
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
            id = "1",
            title = getString(R.string.events),
            icon = "",
            color = "",
        )
        attractionCategoryList.add(all)
        attractionCategoryList.add(events)
    }

    private fun populateRV() {
        attractionCategoryList.apply {

            add(AttractionCategory(
                id = "",
                title = getString(R.string.all),
                icon = "",
                color = "",
            )
            )
            add(AttractionCategory(
                id = "",
                title = getString(R.string.events),
                icon = "",
                imgSelected = 0,
                imgUnSelected = 0,
                isSelected = true,
                selectedSvg = "",
                unselectedSvg = "",
                color = "",
            )
            )
            add(AttractionCategory(
                id = "",
                title = "Museums",
                icon = "",
                imgSelected = 0,
                imgUnSelected = 0,
                isSelected = true,
                selectedSvg = "",
                unselectedSvg = "",
                color = "",
            )
            )

            add(AttractionCategory(
                id = "",
                title = "Heritage",
                icon = "",
                imgSelected = 0,
                imgUnSelected = 0,
                isSelected = true,
                selectedSvg = "",
                unselectedSvg = "",
                color = "",
            )
            )
            add(AttractionCategory(
                id = "",
                title = "Art Gallery",
                icon = "",
                imgSelected = 0,
                imgUnSelected = 0,
                isSelected = true,
                selectedSvg = "",
                unselectedSvg = "",
                color = "",
            )
            )
            add(AttractionCategory(
                id = "",
                title = "Festivals",
                icon = "",
                imgSelected = 0,
                imgUnSelected = 0,
                isSelected = true,
                selectedSvg = "",
                unselectedSvg = "",
                color = "",
            )
            )
            add(AttractionCategory(
                id = "",
                title = "Libraries",
                icon = "",
                imgSelected = 0,
                imgUnSelected = 0,
                isSelected = true,
                selectedSvg = "",
                unselectedSvg = "",
                color = "",
            )
            )
        }

    }

    private fun mapSetUp() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {

    }
}