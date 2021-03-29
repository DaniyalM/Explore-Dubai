package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.databinding.FragmentExploreMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.SingleSelectionAdapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class ExploreMapFragment : BaseFragment<FragmentExploreMapBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    private val attractions: ArrayList<AttractionCategory> = ArrayList<AttractionCategory>()
    var mAdapter: SingleSelectionAdapter? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreMapBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapSetUp()
        setupRecyclerView()


    }

    private fun setupRecyclerView() {
        populateRV()
        mAdapter = SingleSelectionAdapter(requireContext(),
            attractions,
            object : SingleSelectionAdapter.InvokeListener {
                override fun getRowPosition(position: Int) {
                Toast.makeText(requireContext(),"${position}",Toast.LENGTH_SHORT).show()
                }})
        binding.rvMapHeader.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }


    private fun populateRV() {
        attractions.apply {

            add(AttractionCategory(
                id = "",
                title = "All",
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
                title = "Events",
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