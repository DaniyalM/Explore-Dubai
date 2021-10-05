package com.app.dubaiculture.ui.postLogin.plantrip.steps.step3

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentTripStep2Binding
import com.app.dubaiculture.databinding.FragmentTripStep3Binding
import com.app.dubaiculture.ui.base.BaseFragment
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
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
@AndroidEntryPoint
class TripStep3Fragment : BaseFragment<FragmentTripStep3Binding>(), OnMapReadyCallback {

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
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        locationPermission()
        mapSetUp(savedInstanceState)

    }

    fun onPreviousClicked() {
        back()
    }

    private fun mapSetUp(savedInstanceState: Bundle?) {


        mapView = binding.root.findViewById(R.id.map)
        mapView?.let {
            it.getMapAsync(this)
            it.onCreate(savedInstanceState)

        }
    }

    override fun onMapReady(map: GoogleMap) {

        map.addMarker(
            MarkerOptions()
                .position(LatLng(24.8623077, 67.0605548))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))

        )

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(24.8623077, 67.0605548), 14.0f
            )
        )
        map.cameraPosition.target


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
//                        loc = location
//                        Timber.e("Current Location ${location.latitude}")
//                        if (eventObj.latitude!!.isNotEmpty() && eventObj.longitude!!.isNotEmpty())
//                            binding.eventDetailInnerLayout.tvKm.text = locationHelper.distance(
//                                loc.latitude,
//                                loc.longitude,
//                                eventObj.latitude!!.toDouble(),
//                                eventObj.longitude!!.toDouble()
//                            ).toString() + " " + resources.getString(R.string.away)
                    }
                },
                locationCallback
            )
        }
    }

}