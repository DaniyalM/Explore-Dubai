package com.app.dubaiculture.ui.postLogin.explore.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.ExploreMap
import com.app.dubaiculture.databinding.FragmentExploreButtomSheetBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.DirectionClickListener
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.explore.map.adapter.ExploreMapAdapter
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.CATEGORY
import com.app.dubaiculture.utils.Constants.NavBundles.EXPLORE_MAP_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreBottomSheetFragment : BaseBottomSheetFragment<FragmentExploreButtomSheetBinding>() {
    private lateinit var exploreMapList: ArrayList<ExploreMap>


    lateinit var exploreNearAdapter: ExploreMapAdapter
    private var lat: Double? = null
    private var lng: Double? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            lat = getDouble(Constants.NavBundles.LOCATION_LAT)
            lng = getDouble(Constants.NavBundles.LOCATION_LNG)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreButtomSheetBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.apply {
            exploreMapList = getParcelableArrayList(EXPLORE_MAP_LIST)!!
            binding.headingMuseumsNear.text = "${getString(CATEGORY) + " ${resources.getString(R.string.near_you)}" }"
            rvSetUp(exploreMapList)
        }


    }
    private fun rvSetUp(list: List<ExploreMap>) {
        exploreNearAdapter = ExploreMapAdapter(isArabic(), object : RowClickListener{
            override fun rowClickListener(position: Int) {
            }

            override fun rowClickListener(position: Int, imageView: ImageView) {

            }

        },object : DirectionClickListener{
            override fun directionClickListener(position: Int) {
                val mapView = list[position]
                if (!mapView.lat.isNullOrEmpty() && !mapView.lng.isNullOrEmpty()) {
                    // open google map application
                    navigateToGoogleMap(
                        lat.toString(),
                        lat.toString(),
                        mapView.lat.toString(),
                        mapView.lng.toString()
                    )
                }
            }

        })
        binding.rvListing.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = exploreNearAdapter
        }
        exploreNearAdapter.explore = list
        exploreNearAdapter.notifyDataSetChanged()
    }
}