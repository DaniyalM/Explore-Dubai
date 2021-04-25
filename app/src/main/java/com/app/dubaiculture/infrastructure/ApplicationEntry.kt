package com.app.dubaiculture.infrastructure

import android.app.Application
import com.app.dubaiculture.utils.Constants.IBecons.IDENTIFIER
import com.app.dubaiculture.utils.Constants.IBecons.UUID_BECON
import com.app.dubaiculture.utils.NetworkLiveData
import com.app.dubaiculture.utils.PushNotificationManager
import com.app.dubaiculture.utils.ThemeUtil
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
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
        beaconManager.connect {
          beaconManager.startMonitoring(BeaconRegion(
              IDENTIFIER,
                UUID.fromString(UUID_BECON), null, null))

        }

//        beaconManager.connect {
//            BeaconManager.ServiceReadyCallback {
//                Timber.e("Service ready Start Monitoring...")
//
//                region = BeaconRegion("ranged region",
//                    UUID.fromString("be61acf9-cac3-4645-abcb-84955b5cac5b"), null, null)
//           region =      beaconManager.startMonitoring(BeaconRegion(
//               "monitored region",
//               UUID.fromString("be61acf9-cac3-4645-abcb-84955b5cac5b"),
//               null, null))
//                beaconManager.startRanging(region)

//            }
//        }


        isInternetActive = NetworkLiveData.isInternetAvailable()
        Timber.plant(Timber.DebugTree())
        ThemeUtil.applyTheme("light")

    }


}