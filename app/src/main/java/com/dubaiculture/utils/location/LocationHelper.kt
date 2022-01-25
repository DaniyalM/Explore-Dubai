package com.dubaiculture.utils.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dubaiculture.R
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import javax.inject.Inject
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("MissingPermission")
open class LocationHelper @Inject constructor(
    val fusedLocationProviderClient: FusedLocationProviderClient,
    val locationRequest: LocationRequest,
    val locationManager: LocationManager,
    val activity: Context
) {
    private var context: Context? = null
    fun provideContext(context: Context) {
        this.context = context
    }


    fun newLocationData(locationCallback: LocationCallback) {
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        context.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.myLooper()
            )
        }

    }

    fun locationSetUp(
        locationLatLongInterface: LocationLatLng,
        locationCallback: LocationCallback

    ) {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            try{
                val location: Location? = task.result
                if (location == null) {
                    newLocationData(locationCallback)
                } else {
                    locationLatLongInterface.getCurrentLocation(location)
                }
            }catch (ex:Exception){

            }
        }


    }

    interface LocationLatLng {
        fun getCurrentLocation(location: Location)
    }


    //     24.877287306864435, 67.06273232147993
//     24.91420473643946, 67.18402864665703
    open fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1))
                * sin(deg2rad(lat2))
                + (cos(deg2rad(lat1))
                * cos(deg2rad(lat2))
                * cos(deg2rad(theta))))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return milesToKm(dist)
    }

    open fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    open fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }


    //    open fun milesToKm(dist : Double): Double{
//        val km = dist / 0.62137
//        return String.format("%.1f", km).toDouble()
//    }
    open fun milesToKm(dis: Double): Double {
        val km = dis / 0.62137
        return Math.round(km * 10).toDouble() / 10
    }

    fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }



}


