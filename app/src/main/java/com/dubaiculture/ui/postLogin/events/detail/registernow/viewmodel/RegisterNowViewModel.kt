package com.dubaiculture.ui.postLogin.events.detail.registernow.viewmodel

import android.app.Application
import android.net.Uri
import android.widget.TextView
import androidx.core.net.toFile
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.event.EventRepository
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.FileUtils
import com.dubaiculture.utils.event.Event
import com.jaiselrahman.filepicker.model.MediaFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterNowViewModel @Inject constructor(
    application: Application, private val eventRepository: EventRepository,
) : BaseViewModel(application) {

    private var _isRegistered = MutableLiveData<Event<Boolean>>()
    var isRegistered: LiveData<Event<Boolean>> = _isRegistered

    // booleans for checking regex validation
    val isSlot = MutableLiveData<Boolean?>(true)
    val isImageURI = MutableLiveData<Boolean?>(true)

    var slot: ObservableField<String> = ObservableField("")
    var image: ObservableField<String> = ObservableField("")

    private var _slotError = MutableLiveData<Int>()
    var slotError: LiveData<Int> = _slotError

    private var _imageError = MutableLiveData<Int>()
    var imageError: LiveData<Int> = _imageError

    fun onTimeSlotChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        slot.set(s.toString())
        if (slot.get().toString().trim().isNullOrEmpty()) {
            isSlot.value = false
            _slotError.value = R.string.required
        }
        if (!slot.get().toString().trim().isNullOrEmpty()) {
            isSlot.value = true
            _slotError.value = R.string.no_error
        }
    }

     fun registerEvent(
        eventId:String,
        slotId:String ,
        occupation: String,
        culture: String,
        file : MultipartBody.Part? = null
    ){
        viewModelScope.launch {
            showLoader(true)
            val result =   eventRepository.submitRegister(
                eventId.trim(),
                slotId.trim(),
                occupation.trim(),
                    culture.trim(),
                file
            )
            if(result is Result.Success){
                showLoader(false)
                _isRegistered.value = Event(true)
                showToast(result.value.Result.message)
                Timber.e("" + result.value.Result.message)

            }else if(result is Result.Failure){
                showLoader(false)
                Timber.e("" + result)
            }
        }
    }

    fun getFile(fileList : ArrayList<MediaFile>, fileUtil : FileUtils , fileTextView : TextView){
      val name =  fileList[0].name
        val path = fileList[0].uri.path
        val fileSize =  fileUtil.calculateFileSize(File(path))
        Timber.e("File Size=>"+fileSize)
        if(fileSize>2){
            fileTextView.text =""
            showToast("PDF format not exceeding 2MB")
        }else{
            fileTextView.text =name
        }
    }

    fun getImage(imgName : String ,path : String , fileUtil : FileUtils , fileTextView : TextView){
        val fileSize =  fileUtil.calculateFileSize(Uri.fromFile(File(path)).toFile())
        Timber.e("File Size=>"+fileSize)
        if(fileSize>2){
            fileTextView.text =""
            showToast("PDF format not exceeding 2MB")
        }else{
            fileTextView.text =imgName
        }
    }
}