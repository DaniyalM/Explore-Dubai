package com.dubaiculture.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.dubaiculture.data.repository.visited.VisitedRepository
import com.dubaiculture.ui.preLogin.PreLoginActivity
import com.dubaiculture.utils.Constants.IBecons.IDENTIFIER
import com.dubaiculture.utils.Constants.IBecons.MAJOR
import com.dubaiculture.utils.Constants.IBecons.MINOR
import com.dubaiculture.utils.Constants.IBecons.UUID_BECON
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import java.util.*
import javax.inject.Inject

class BeaconUtils @Inject constructor(
    private val context: Context,
//    private val visitedRepository: VisitedRepository,
) {
    var beaconManager: BeaconManager = BeaconManager(context)
    var region: BeaconRegion = BeaconRegion(IDENTIFIER, UUID.fromString(UUID_BECON), MAJOR, MINOR)
//    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun beaconDisconnect(){
        beaconManager.stopMonitoring(region.identifier)
        beaconManager.stopRanging(region)
        beaconManager.disconnect()
    }
    fun beaconConnect() {
        beaconManager.connect {

            beaconManager.startMonitoring(region)
            beaconManager.startRanging(region)
            beaconManager.setScanStatusListener(object : BeaconManager.ScanStatusListener {
                override fun onScanStart() {
                    Toast.makeText(context,"Dubai Culture Scanning has been started", Toast.LENGTH_SHORT).show()
//                    PushNotificationManager.showNotification(
//                        context,
//                        "Beacon Scanning",
//                        "Dubai Culture Scanning has been started", null
//                    )
                }

                override fun onScanStop() {
                }
            })
//            startMontioring()
        }
    }

     fun startMontioring() {
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
                    "You are close to Attraction.",
                    "Current security wait time is 15 minutes, "
                            + "and it's a 5 minute walk from security to the gate. "
                            + "Looks like you've got plenty of time!",
                    resultPendingIntent
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