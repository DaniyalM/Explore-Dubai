package com.app.dubaiculture.ui.postLogin.events.detail.registernow.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.EventRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber

class RegisterNowViewModel @ViewModelInject constructor(application: Application, private val eventRepository: EventRepository,
) : BaseViewModel(application) {

    private var _isRegistered = MutableLiveData<Event<Boolean>>()
    var isRegistered: LiveData<Event<Boolean>> = _isRegistered
     fun registerEvent(
        eventId:String,
        slotId:String ,
        occupation: String,
        file : MultipartBody.Part? = null
    ){
        viewModelScope.launch {
            showLoader(true)
            val result =   eventRepository.submitRegister(
                eventId.trim(),
                slotId.trim(),
                occupation.trim(),
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
}