package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.ServiceRepository
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class DescriptionViewModel @Inject constructor(
    application: Application,
    private val serviceRepository: ServiceRepository
) : BaseViewModel(application) {
    private val _docBody: MutableLiveData<Event<ResponseBody>> = MutableLiveData()
    val docBody: LiveData<Event<ResponseBody>> = _docBody
    private val context = getApplication<ApplicationEntry>()


    fun getDoc(url: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = serviceRepository.getDoc(url)) {
                is Result.Success -> {
                    showLoader(false)
                    result.value?.let {
                        _docBody.value = Event(it)
                    }

                }
            }
        }

    }


    fun saveFile(body: ResponseBody?): String {
        val pathWhereYouWantToSaveFile = context.filesDir.absolutePath + "/doc.pdf"

        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            //val file = File(getCacheDir(), "cacheFileAppeal.srl")
            val fos = FileOutputStream(pathWhereYouWantToSaveFile)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        } catch (e: Exception) {
            Timber.e("saveFile $e")
        } finally {
            input?.close()
        }
        return ""
    }


}