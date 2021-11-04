package com.app.dubaiculture.ui.postLogin.plantrip.steps.step3

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.LocationNearest
import com.app.dubaiculture.databinding.FragmentTripStep3Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter.NearestLocationAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter.clicklisteners.NearestLocationClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.Step3ViewModel
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TripStep3Fragment : BaseFragment<FragmentTripStep3Binding>(), OnMapReadyCallback {

    private lateinit var markerLocation: LatLng
    private var marker: Marker? = null
    private lateinit var nearestLocationList: List<LocationNearest>
    private lateinit var location: Location
    private var mMap: GoogleMap? = null
    private val step3ViewModel: Step3ViewModel by viewModels()
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
        subscribeUiEvents(step3ViewModel)
        lottieAnimationRTL(binding.animationView)
        setupRV()
    }

    private fun setupRV() {

        binding.rvLocationChips.apply {
            layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            nearestLocAdapter = NearestLocationAdapter(
                object : NearestLocationClickListener {
                    override fun rowClickListener(userType: LocationNearest) {
//                        showToast(userType.title)
                    }

                    override fun rowClickListener(userType: LocationNearest, position: Int) {
                        tripSharedViewModel.updateLocationItem(userType.copy(isChecked = !userType.isChecked!!))

//                        navigateMarker(
//                            if (userType.latitude.isBlank()) 0.0 else userType.latitude.toDouble(),
//                            if (userType.longitude.isBlank()) 0.0 else userType.longitude.toDouble()
//                        )

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

        step3ViewModel.nearestLocation.observe(viewLifecycleOwner) {

//            tripSharedViewModel._nearestLocation.value = it
            tripSharedViewModel._nearestLocationType.value = it.nearestLocation

        }

        tripSharedViewModel.type.observe(viewLifecycleOwner) {
            tripSharedViewModel.updateInLocationList(it)
        }



        tripSharedViewModel.nearestLocation.observe(viewLifecycleOwner) {
            binding.rvLocationChips.visibility = View.VISIBLE
            nearestLocAdapter.submitList(it)
            setMarker(it)
//            nearestLocationList = it

        }

    }

    private fun setMarker(it: List<LocationNearest>) {
        it.map { selectedLoc ->
            if (selectedLoc.isChecked) {
                markerLocation = LatLng(
                    if (selectedLoc.latitude.isBlank()) 0.0 else selectedLoc.latitude.toDouble(),
                    if (selectedLoc.longitude.isBlank()) 0.0 else selectedLoc.longitude.toDouble()
                )
                navigateMarker(
                    if (selectedLoc.latitude.isBlank()) 0.0 else selectedLoc.latitude.toDouble(),
                    if (selectedLoc.longitude.isBlank()) 0.0 else selectedLoc.longitude.toDouble()
                )
            }
        }
    }


    fun onPreviousClicked() {
        customNavigation.navigateStep(false, R.id.tripStep3)
    }

    fun onNextClicked() {
        if (tripSharedViewModel.validateStep3()) {
            customNavigation.navigateStep(true, R.id.tripStep3)
        } else {
            showToast(getString(R.string.selectLocation))
        }
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
        if(this::markerLocation.isInitialized){
            navigateMarker(markerLocation.latitude,markerLocation.longitude)
        }else{
            locationPermission()
        }
//

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

                        navigateMarker(location.latitude, location.longitude)

                    }
                },
                locationCallback
            )
        }
    }

    fun navigateMarker(latitude: Double, longitude: Double) {
        if (mMap != null) {
            if (marker == null) {

                marker = mMap?.addMarker(
                    MarkerOptions()
                        .position(LatLng(latitude, longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))

                )
            } else {
                marker!!.position = LatLng(latitude, longitude)
            }


            mMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitude, longitude), 14.0f
                )
            )
            mMap?.cameraPosition?.target
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