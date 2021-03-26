package com.app.dubaiculture.ui.base

import android.app.Application
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.event.UiEvent

abstract class BaseViewModel(
    application: Application,
    private val baseRespository: BaseRepository? = null,
) : AndroidViewModel(application) {
    private val _uiEventsLiveData = MutableLiveData<Event<UiEvent>>()
    val uiEvents: LiveData<Event<UiEvent>> = _uiEventsLiveData

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLiveData


    protected var _isFavourite: MutableLiveData<String> = MutableLiveData()
    var isFavourite: LiveData<String> = _isFavourite


    fun showLoader(show: Boolean) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowLoader(show)))
    }

    fun showAlert(title: String = "Alert", message: String) {
        _uiEventsLiveData.value = Event(UiEvent.ShowAlert(title = title, message = message))
    }

    fun showErrorDialog(
        title: String? = "Alert",
        message: String,
        @ColorRes colorBg: Int? = R.color.purple_900,
    ) {
        _uiEventsLiveData.value = Event(UiEvent.ShowErrorDialog(title = title ?: "Alert",
            message = message,
            colorBg = colorBg ?: R.color.red_600))
    }

    fun showToast(message: String) {
        _uiEventsLiveData.value = Event(UiEvent.ShowToast(message))
//        _uiEventsLiveData.postValue(Event(UiEvent.ShowToast(message)))

    }


    fun showSnackbar(message: String, action: (() -> Unit)? = null) {
        _uiEventsLiveData.value = Event(UiEvent.ShowSnackbar(message, action))

    }

    fun navigateByDirections(navDirections: NavDirections) {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByDirections(navDirections))
    }

    fun navigateByAction(actionId: Int, bundle: Bundle? = null) {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByAction(actionId, bundle))
    }

    fun setUser(user: User) {
        _userLiveData.value = user
    }

}