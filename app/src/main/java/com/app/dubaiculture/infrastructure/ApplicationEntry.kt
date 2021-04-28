package com.app.dubaiculture.infrastructure

import android.R
import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.utils.AppConfigUtils.CHANNEL_ID
import com.app.dubaiculture.utils.Constants.IBecons.IDENTIFIER
import com.app.dubaiculture.utils.Constants.IBecons.UUID_BECON
import com.app.dubaiculture.utils.NetworkLiveData
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
    lateinit var region: BeaconRegion
    override fun onCreate() {
        super.onCreate()
        auth = AuthState()
        NetworkLiveData.initNetwork(this)
        PushNotificationManager.createNotificationChannel(this)
        beaconManager = BeaconManager(applicationContext)

        beaconManager.connect(object : BeaconManager.ServiceReadyCallback {
            override fun onServiceReady() {
//                showNotification("Beacon Monitoring", "Service ready Start Monitoring...")
                Toast.makeText(applicationContext,"Service ready Start Monitoring...",Toast.LENGTH_SHORT).show()

                region = BeaconRegion(IDENTIFIER, UUID.fromString(UUID_BECON), null, null)
                beaconManager.startMonitoring(region)
                beaconManager.startRanging(region)
                beaconManager.setScanStatusListener(object :BeaconManager.ScanStatusListener{
                    override fun onScanStart() {
                        PushNotificationManager.showNotification(applicationContext,
                                "Beacon Scanning has begin.",
                                "Dubai Culture Scanning has been started", null)
//                      Toast.makeText(applicationContext,"Scanning has been started",Toast.LENGTH_SHORT).show()
                    }

                    override fun onScanStop() {
                        TODO("Not yet implemented")
                    }
                })



                beaconManager.setMonitoringListener(object : BeaconManager.BeaconMonitoringListener {
                    override fun onEnteredRegion(beaconRegion: BeaconRegion?, beacons: MutableList<Beacon>?) {
                        Toast.makeText(applicationContext,"Monitoring has been started",Toast.LENGTH_SHORT).show()

                        PushNotificationManager.showNotification(applicationContext,
                                "Your gate closes in 47 minutes.",
                                "Current security wait time is 15 minutes, "
                                        + "and it's a 5 minute walk from security to the gate. "
                                        + "Looks like you've got plenty of time!", null)
                    }

                    override fun onExitedRegion(beaconRegion: BeaconRegion?) {
                        TODO("Not yet implemented")
                    }

                })
            }
        })



        isInternetActive = NetworkLiveData.isInternetAvailable()
        Timber.plant(Timber.DebugTree())
        ThemeUtil.applyTheme("light")

    }




}