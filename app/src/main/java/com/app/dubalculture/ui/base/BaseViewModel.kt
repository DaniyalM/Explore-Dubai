package com.app.dubalculture.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.dubalculture.utils.event.Event
import com.app.dubalculture.utils.event.UiEvent
import com.app.dubalculture.data.Result

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiEventsLiveData = MutableLiveData<Event<UiEvent>>()
    val uiEvents: LiveData<Event<UiEvent>> = _uiEventsLiveData


    fun showLoader(show: Boolean) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowLoader(show)))
    }

    fun showAlert(title: String = "Alert", message: String) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowAlert(title = title, message = message)))
    }

    fun showToast(message: String) {
//        _uiEventsLiveData.value = Event(UiEvent.ShowToast(message))
        _uiEventsLiveData.postValue(Event(UiEvent.ShowToast(message)))

    }

    fun showSnackbar(message: String, action: (() -> Unit)? = null) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowSnackbar(message, action)))

    }

    fun handleApiError(
        failure: Result.Failure,
        retry: (() -> Unit)? = null
    ) {
        when {
            failure.isNetWorkError -> showSnackbar("Please check your connection", retry)
            failure.errorCode == 400 -> showSnackbar("Content not found", retry)
            else -> {
                val error = failure.errorBody?.string().toString()
                showSnackbar(error)
            }
        }

    }
}