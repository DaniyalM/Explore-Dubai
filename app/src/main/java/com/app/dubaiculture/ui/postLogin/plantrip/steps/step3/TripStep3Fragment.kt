package com.app.dubaiculture.ui.postLogin.plantrip.steps.step3

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.LocationNearest
import com.app.dubaiculture.data.repository.trip.local.NearestLocation
import com.app.dubaiculture.data.repository.trip.local.UsersType
import com.app.dubaiculture.databinding.FragmentTripStep3Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.UserTypeAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.clicklisteners.UserTypeClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter.NearestLocationAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter.clicklisteners.NearestLocationClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.Step2ViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.app.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.app.dubaiculture.utils.Constants.NavBundles.NEAREST_LOCATION



@AndroidEntryPoint
class TripStep3Fragment : BaseFragment<FragmentTripStep3Binding>(), OnMapReadyCallback {

    private lateinit var nearestLocationList: List<LocationNearest>
    private lateinit var location: Location
    private lateinit var mMap: GoogleMap
//    private val tripSharedViewModel: tripSharedViewModel by viewModels()
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()

    private lateinit var nearestLocAdapter: NearestLocationAdapter


    companion object {
        lateinit var customNavigation: CustomNavigation
    }

    private var mapView: MapView? = null

    @Inject
    lateinit var locationHelper: LocationHelper

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            //  locationIsEmpty(locationResult.lastLocation)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep3Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        lottieAnimationRTL(binding.animationView)
        setupRV()
    }

    private fun setupRV() {

        binding.rvLocationChips.apply {
            layoutManager =
                GridLayoutManager(context, 3)
            nearestLocAdapter = NearestLocationAdapter(
                object : NearestLocationClickListener {
                    override fun rowClickListener(userType: LocationNearest) {
//                        showToast(userType.title)
                    }

                    override fun rowClickListener(userType: LocationNearest, position: Int) {

                        tripSharedViewModel.updateUserItem(userType.copy(isChecked = !userType.isChecked!!))
                    }

                }
            )
            adapter = nearestLocAdapter


        }

    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()
        mapView?.onResume()

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mapSetUp(savedInstanceState)
        subscribeToObservables()


    }

    private fun subscribeToObservables() {

        tripSharedViewModel.nearestLocation.observe(viewLifecycleOwner) {

            tripSharedViewModel._usersType.value = it.nearestLocation

        }

        tripSharedViewModel.type.observe(viewLifecycleOwner) {
            tripSharedViewModel.updateInUserTypeList(it)
        }



        tripSharedViewModel.usersType.observe(viewLifecycleOwner) {

            binding.rvLocationChips.visibility = View.VISIBLE
            nearestLocAdapter.submitList(it)
//            nearestLocationList = it

        }

    }


    fun onPreviousClicked() {
        customNavigation.navigateStep(false, R.id.tripStep3)
    }

    fun onNextClicked() {
        customNavigation.navigateStep(true, R.id.tripStep3)
    }
    fun onAddLocation() {
//        val bundle = bundleOf(NEAREST_LOCATION to nearestLocationList)
        navigate(R.id.action_step3Fragment_to_addLocFragment)
    }

    fun onLocationClicked() {
        locationPermission()
    }

    private fun mapSetUp(savedInstanceState: Bundle?) {


        mapView = binding.root.findViewById(R.id.map)
        mapView?.let {
            it.getMapAsync(this)
            it.onCreate(savedInstanceState)

        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        locationPermission()

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
                    @SuppressLint("SetTextI18n")
                    override fun getCurrentLocation(location: Location) {

                        mMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(location.latitude, location.longitude))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))

                        )

                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(location.latitude, location.longitude), 14.0f
                            )
                        )
                        mMap.cameraPosition.target

                    }
                },
                locationCallback
            )
        }
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()

    }

}