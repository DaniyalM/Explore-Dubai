package com.app.dubaiculture.infrastructure

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.utils.Constants.IBecons.IDENTIFIER
import com.app.dubaiculture.utils.Constants.IBecons.UUID_BECON
import com.app.dubaiculture.utils.NetworkLiveData
import com.app.dubaiculture.utils.PreferenceRepository
import com.app.dubaiculture.utils.PushNotificationManager
import com.app.dubaiculture.utils.ThemeUtil
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import com.squareup.otto.Bus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.*


@HiltAndroidApp
class ApplicationEntry : Application() {
    var bus: Bus = Bus()
    lateinit var auth: AuthState
    var isInternetActive = false
    lateinit var beaconManager: BeaconManager
    lateinit var preferenceRepository: PreferenceRepository

    lateinit var region: BeaconRegion
    override fun onCreate() {
        super.onCreate()
        auth = AuthState()
        NetworkLiveData.initNetwork(this)
        PushNotificationManager.createNotificationChannel(this)
        beaconManager = BeaconManager(applicationContext)
        beaconImplementation()
        isInternetActive = NetworkLiveData.isInternetAvailable()
        Timber.plant(Timber.DebugTree())
//        ThemeUtil.applyTheme("dark")
        preferenceRepository = PreferenceRepository(
                getSharedPreferences(DEFAULT_PREFERENCES, MODE_PRIVATE)
        )
    }

    private fun beaconImplementation(){
        beaconManager.connect(object : BeaconManager.ServiceReadyCallback {
            override fun onServiceReady() {
                region = BeaconRegion(IDENTIFIER, UUID.fromString(UUID_BECON), null, null)
                beaconManager.startMonitoring(region)
                beaconManager.startRanging(region)
                beaconManager.setScanStatusListener(object : BeaconManager.ScanStatusListener {
                    override fun onScanStart() {
                        PushNotificationManager.showNotification(
                            applicationContext,
                            "Beacon Scanning",
                            "Dubai Culture Scanning has been started", null
                        )
                    }

                    override fun onScanStop() {
                    }
                })
                beaconManager.setMonitoringListener(object :
                    BeaconManager.BeaconMonitoringListener {
                    override fun onEnteredRegion(
                        beaconRegion: BeaconRegion?,
                        beacons: MutableList<Beacon>?
                    ) {

                        val intent  = Intent(applicationContext,PreLoginActivity::class.java)
                        val resultPendingIntent =PendingIntent.getActivity(applicationContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)
                        PushNotificationManager.showNotification(
                            applicationContext,
                            "Your gate closes in 47 minutes.",
                            "Current security wait time is 15 minutes, "
                                    + "and it's a 5 minute walk from security to the gate. "
                                    + "Looks like you've got plenty of time!", resultPendingIntent
                        )
                    }
                    override fun onExitedRegion(beaconRegion: BeaconRegion?) {
                    }

                })
            }
        })
        isInternetActive = NetworkLiveData.isInternetAvailable()
        Timber.plant(Timber.DebugTree())
//        ThemeUtil.applyTheme("dark")
    }




companion object {
    const val DEFAULT_PREFERENCES = "default_preferences"
}

}