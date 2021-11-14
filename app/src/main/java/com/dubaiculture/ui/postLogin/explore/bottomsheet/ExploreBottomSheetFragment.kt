package com.dubaiculture.ui.postLogin.explore.bottomsheet

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.explore.local.models.ExploreMap
import com.dubaiculture.databinding.FragmentExploreButtomSheetBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.events.`interface`.DirectionClickListener
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.ui.postLogin.explore.map.adapter.ExploreMapAdapter
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.NavBundles.CATEGORY
import com.dubaiculture.utils.Constants.NavBundles.EXPLORE_MAP_LIST
import com.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreBottomSheetFragment : BaseBottomSheetFragment<FragmentExploreButtomSheetBinding>() {
    private lateinit var exploreMapList: ArrayList<ExploreMap>

    @Inject
    lateinit var locationHelper: LocationHelper


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            exploreMapList = getParcelableArrayList(EXPLORE_MAP_LIST)!!
            binding.headingMuseumsNear.text =
                "${getString(CATEGORY) + " ${resources.getString(R.string.near_you)}"}"
            rvSetUp(exploreMapList)
        }
    }


    private fun rvSetUp(list: List<ExploreMap>) {
        exploreNearAdapter = ExploreMapAdapter(isArabic(), object : RowClickListener {
            override fun rowClickListener(position: Int) {
            }

            override fun rowClickListener(position: Int, imageView: ImageView) {

            }

        }, object : DirectionClickListener {
            override fun directionClickListener(position: Int) {
                val mapView = list[position]
                if (!mapView.lat.isNullOrEmpty() && !mapView.lng.isNullOrEmpty()) {
                    // open google map application
                    locationHelper.locationSetUp(object : LocationHelper.LocationLatLng {
                        override fun getCurrentLocation(location: Location) {
                            navigateToGoogleMap(
                                location.latitude.toString(),
                                location.longitude.toString(),
                                mapView.lat.toString(),
                                mapView.lng.toString()
                            )
                        }
                    }, object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
//                        Timber.e("LocationCallback ${locationResult.lastLocation.latitude}")
                        }
                    })

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