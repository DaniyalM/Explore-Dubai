package com.app.dubaiculture.utils.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("MissingPermission")
open class LocationHelper @Inject constructor(
    val fusedLocationProviderClient: FusedLocationProviderClient,
    val locationRequest: LocationRequest
) {



    fun newLocationData(activity: Context,locationCallback: LocationCallback) {
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        activity.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.myLooper()
            )
        }

    }

    fun locationSetUp(
        iface: LocationLatLng,
        activity: Context,
        locationCallback: LocationCallback
    ) {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location: Location? = task.result
            if (location == null) {
                newLocationData(activity,locationCallback)
//                object : LocationCallback() {
//                    override fun onLocationResult(p0: LocationResult?) {
//                        super.onLocationResult(p0)
//                        locationCallback.getLocationResultCallback(p0)
//                    }
//                }
            } else {
                iface.getCurrentLocation(location)
            }
        }


    }

    interface LocationLatLng {
        fun getCurrentLocation(location: Location)
    }

    interface LocationCallBack {
        fun getLocationResultCallback(locationResult: LocationResult?)
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Timber.e("CallBack => ${locationResult.lastLocation.toString()}")
        }
    }

    //     24.877287306864435, 67.06273232147993
//     24.91420473643946, 67.18402864665703
    open fun distance(lat1: Double, lon1: Double, lat2: Double? =24.83250180519734, lon2: Double ?=67.08119661055807): Double {
        val theta = lon1 - (lon2?:67.08119661055807)
        var dist = (sin(deg2rad(lat1))
                * sin(deg2rad(lat2?:24.83250180519734))
                + (cos(deg2rad(lat1))
                * cos(deg2rad(lat2?:24.83250180519734))
                * cos(deg2rad(theta))))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return milesToKm(dist)
    }

    open fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    open fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }


    open fun milesToKm(dist : Double): Double{
        val km = dist / 0.62137
        return String.format("%.1f", km).toDouble()
    }
}


