package com.dubaiculture.infrastructure

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dubaiculture.utils.BeaconUtils
import com.dubaiculture.utils.NetworkLiveData
import com.dubaiculture.utils.PreferenceRepository
import com.dubaiculture.utils.PushNotificationManager
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.service.BeaconManager
import com.squareup.otto.Bus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class ApplicationEntry : Application() {
    var bus: Bus = Bus()
    lateinit var auth: AuthState
    var isInternetActive = false
    lateinit var beaconManager: BeaconManager
    lateinit var preferenceRepository: PreferenceRepository
    lateinit var region: BeaconRegion

    var appStarted: Boolean = false





    @Inject
    lateinit var beaconUtils: BeaconUtils


    override fun onCreate() {
        super.onCreate()
        auth = AuthState()
        NetworkLiveData.initNetwork(this)
        PushNotificationManager.createNotificationChannel(this)
        beaconUtils.beaconConnect()
        beaconManager = beaconUtils.beaconManager
        region = beaconUtils.region
        isInternetActive = NetworkLiveData.isInternetAvailable()
        Timber.plant(Timber.DebugTree())
        preferenceRepository = PreferenceRepository(
            getSharedPreferences(DEFAULT_PREFERENCES, MODE_PRIVATE)
        )
//        preferenceRepository.isDarkTheme = true

    }


    companion object {
        const val DEFAULT_PREFERENCES = "default_preferences"
    }

}