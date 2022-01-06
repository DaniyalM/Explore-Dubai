package com.dubaiculture.ui.widgets.communication

import android.content.Context
import android.location.Location
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dubaiculture.cronjobs.BaseWorker
import com.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import javax.inject.Inject

@HiltWorker
class WidgetWorker @AssistedInject constructor(
    @Assisted val ctx: Context,
    @Assisted params: WorkerParameters,
    private val locationHelper: LocationHelper

) : BaseWorker(ctx, params) {
    override fun doWork(): Result {
        locationWorking()

        repeat(10){
            Timber.e("Working $it")
        }


        return Result.success()
    }


    fun locationWorking(){
        locationHelper.locationSetUp(object :LocationHelper.LocationLatLng{
            override fun getCurrentLocation(location: Location) {
                Timber.e("Location Is ${location.latitude} ${location.longitude}")
            }
        },object :LocationCallback(){
            override fun onLocationResult(lR: LocationResult) {
                Timber.e("Location Is ${lR.lastLocation}")

            }
        })
    }


    companion object {
        const val TAG = "DemoWorker"
    }
}