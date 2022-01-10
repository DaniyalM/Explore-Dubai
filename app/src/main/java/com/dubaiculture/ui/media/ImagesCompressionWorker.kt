package com.dubaiculture.ui.media

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dubaiculture.utils.makeStatusNotification
import com.dubaiculture.utils.sleep
import java.lang.Exception

class ImagesCompressionWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        // doWork() override method is responsible for bg work.
        // Your work here.
        // Your task result is the status .res.success() = means task successful, lastly task wasnot successful
        // return res.failure() and res.retry() task will excute again sometime.
        makeStatusNotification(message = "Image Compression start Working", context = applicationContext)
        sleep()
         return try {
                for(i in 0..3   ){
                    Log.d(TAG, "do Compression Work:  $i")
                    Thread.sleep(1000)
                }
             val dataObj = Data.Builder()
             dataObj.putString("op","success ImageCompressionWokrer")
            Result.success(dataObj.build())
        }catch (exception:Exception){
            Result.failure()
        }
    }


    //step two making work request its has two types ,which define how and when work should be run.
    //1 type OneTimeWorkRequest = return the task only once
    // 2 type PeriodicWorkTimeRequest = Runs the task after a certain time interval.
    //val yourWorkRequest = OneTimeWorkRequestBuilder<YourWorkerClass>().build()
    // once we define then we can easily schedule your task WorkManager.getInstance(context).enqueue(yourWorkRequest)
    // we can use constraints also in it. just like
    /** val myConstraints = Constraints.Builder()     .setRequiresDeviceIdle(true)
    //checks whether device should be idle for the WorkRequest to run
    .setRequiresCharging(true) //checks whether device should be charging for the WorkRequest to run
    .setRequiredNetworkType(NetworkType.CONNECTED) //checks whether device should have Network Connection
    .setRequiresBatteryNotLow(true) // checks whether device battery should have a specific level to run the work request
    .setRequiresStorageNotLow(true) // checks whether device storage should have a specific level to run the work request
    .build() **/
/*   val yourWorkRequest = OneTimeWorkRequestBuilder<YourWorkerClass>()
       .setConstraints(myConstraints)
       .build()*/ // Work request only run/work when these constraint are satisfy.


    //    val periodicWorkReq  = PeriodicWorkRequestBuilder<"workerclass">(1,TimeUnit.HOURS)
//            .build()

    // if u dont want to run task immediately so u can specify your work to start after minimium delay
    // initial delay using.

    /*val yourWorkRequest = OneTimeWorkRequestBuilder<YourWorkerClass>()
          .setInitialDelay(10, TimeUnit.MINUTES) // 10 is mins
          .build()*/

//CHAINING WORKER WITH ANDROID WORK MANAGER
    //1 Sequential beginwith and then A-> B-   2) parallel 3) multiple parallel

    //sequential
    /*WorkManager.getInstance()
           .beginWith(firstWorker)
           .then(secondWorker)
           .enqueue()*/

    //parallel
   /*WorkManager
          .getInstance()
          .beginWith(firstWork,secondWork,thirdWork)
          .then(fourthWorker)
          .then(fifthWorker,sixWorker)
          .enqueue()*/

    // multi parallel.
    /*val workManager: WorkManager = WorkManager.getInstance()
        val chain1 = workManager
                .beginWith(firstCompressFileWorker)
                .then(uploadFileWorker)
        val chain2 = workManager
                .beginWith(secondCompressFileWorker)
                .then(uploadFileWorker)
        val chain3 = workManager
                .beginWith(thirdCompressFileWorker)
                .then(uploadFileWorker)
        WorkContinuation.combine(chain1,chain2,chain3)
                .enqueue()*/

    //atLast
   /*WorkManager.getInstance(context)
           .getWorkInfoByIdLiveData(yourWorkRequest.id)
           .observe(lifecycleOwner, Observer {
                  workInfo ->
                 if (workInfo != null && workInfo.state ==
                        WorkInfo.State.SUCCEEDED)
                       {
                           //Toast
                        }
                      })*/
}