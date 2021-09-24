package com.app.dubaiculture.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.app.dubaiculture.data.repository.visited.VisitedRepository
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.utils.Constants.IBecons.IDENTIFIER
import com.app.dubaiculture.utils.Constants.IBecons.UUID_BECON
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import java.util.*
import javax.inject.Inject

class BeaconUtils @Inject constructor(
    private val context: Context,
    private val visitedRepository: VisitedRepository,
) {
    var beaconManager: BeaconManager = BeaconManager(context)
    var region: BeaconRegion = BeaconRegion(IDENTIFIER, UUID.fromString(UUID_BECON), null, null)
//    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun beaconConnect() {
        beaconManager.connect {

            beaconManager.startMonitoring(region)
            beaconManager.startRanging(region)
            beaconManager.setScanStatusListener(object : BeaconManager.ScanStatusListener {
                override fun onScanStart() {
                    PushNotificationManager.showNotification(
                        context,
                        "Beacon Scanning",
                        "Dubai Culture Scanning has been started", null
                    )
                }

                override fun onScanStop() {
                }
            })
            startMontioring()
        }
    }

    private fun startMontioring() {
        beaconManager.setMonitoringListener(object :
            BeaconManager.BeaconMonitoringListener {
            override fun onEnteredRegion(
                beaconRegion: BeaconRegion?,
                beacons: MutableList<Beacon>?
            ) {
                val intent = Intent(context, PreLoginActivity::class.java)
                val resultPendingIntent =
                    PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                PushNotificationManager.showNotification(
                    context,
                    "Your gate closes in 47 minutes.",
                    "Current security wait time is 15 minutes, "
                            + "and it's a 5 minute walk from security to the gate. "
                            + "Looks like you've got plenty of time!", resultPendingIntent
                )

//                applicationScope.launch {
//                    visitedRepository.addVisitedPlace(AddVisitedPlaceRequest(
//                        deviceId = beacons!![0].uniqueKey,
//                        type = 1,
//                    ))
//                }


            }

            override fun onExitedRegion(beaconRegion: BeaconRegion?) {
            }

        })
    }
}