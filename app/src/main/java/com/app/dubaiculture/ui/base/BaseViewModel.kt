package com.app.dubaiculture.ui.base

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.event.UiEvent
import com.app.dubaiculture.data.Result

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
        _uiEventsLiveData.value = Event(UiEvent.ShowToast(message))
//        _uiEventsLiveData.postValue(Event(UiEvent.ShowToast(message)))

    }

    fun showSnackbar(message: String, action: (() -> Unit)? = null) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowSnackbar(message, action)))

    }

    fun navigateByDirections(navDirections: NavDirections) {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByDirections(navDirections))
    }

    fun navigateByAction(actionId: Int, bundle: Bundle? = null) {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByAction(actionId, bundle))
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