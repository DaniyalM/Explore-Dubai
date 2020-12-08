package com.app.dubaiculture.ui.media

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.dubaiculture.utils.makeStatusNotification
import com.app.dubaiculture.utils.sleep
import java.lang.Exception

class ImagesUploadingWorker(ctx: Context,params:WorkerParameters):Worker(ctx,params) {

    override fun doWork(): Result {
        makeStatusNotification(message = "Image Uploading...", context = applicationContext)
        sleep()
        return try {
            for(i in 0..3){
                Log.d(ContentValues.TAG, "do Image Uploading work: $i")
                Thread.sleep(1000)
            }
            makeStatusNotification(message = "Image Successfully Uploaded", context = applicationContext)
            Result.success()
        }catch (exception: Exception){
            Result.failure()
        }
    }
}