package com.daniyal.basicappimpl.ui.home.viewmodels

import android.app.Application
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import androidx.datastore.preferences.protobuf.LazyStringArrayList
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.work.*
import com.daniyal.basicappimpl.ui.base.BaseViewModel
import com.daniyal.basicappimpl.ui.base.ImagesCompressionWorker
import com.daniyal.basicappimpl.ui.base.ImagesUploadingWorker
import com.daniyal.basicappimpl.utils.AppConfigUtils.IMAGE_MANIPULATION_WORK_NAME
import com.daniyal.basicappimpl.utils.AppConfigUtils.KEY_IMAGE_URI
import com.daniyal.basicappimpl.utils.AppConfigUtils.TAG_OUTPUT

class MainViewModel @ViewModelInject constructor(application: Application) : BaseViewModel(application) {
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>>
    internal var imageUri: Uri? = null

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
        showLoader(true)
        Handler(Looper.getMainLooper()).postDelayed({
            showLoader(false)
        }, 5000)
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }
    internal fun applyCompression(){
        // Add WorkRequest to image Compression work.
        val imgCompressionBuilder = OneTimeWorkRequestBuilder<ImagesCompressionWorker>()
            .setInputData(createInputDataForUri())
            .addTag(TAG_OUTPUT)

        // network constraint
        val networkConstraint =Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        // Add WorkRequest to upload image work and addup network constraint on second work request.
        val imgUploadBuilder = OneTimeWorkRequestBuilder<ImagesUploadingWorker>()
            .setConstraints(networkConstraint)
        val continuation = workManager
            .beginWith(imgCompressionBuilder.build())
            .then(imgUploadBuilder.build())
        // Actually start the work
        continuation.enqueue()
    }
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

}
