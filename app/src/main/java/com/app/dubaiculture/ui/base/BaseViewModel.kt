package com.app.dubaiculture.ui.base

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.ColorRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.utils.GpsStatusListener
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.event.UiEvent
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    application: Application,
    private var baseRespository: BaseRepository? = null,
) : AndroidViewModel(application) {
    val gpsStatusLiveData = GpsStatusListener(application)
    private val _uiEventsLiveData = MutableLiveData<Event<UiEvent>>()
    private val contextViemodel = getApplication<Application>().applicationContext

    val uiEvents: LiveData<Event<UiEvent>> = _uiEventsLiveData

    protected val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLiveData


    protected var _isFavourite: MutableLiveData<Result<AddToFavouriteResponse>> = MutableLiveData()
    var isFavourite: LiveData<Result<AddToFavouriteResponse>> = _isFavourite

//    protected val _user: MutableLiveData<User> = MutableLiveData()
//    val user: LiveData<User> = _user

    fun killSession() {
        Intent(contextViemodel, PreLoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            contextViemodel.startActivity(it)
        }
    }


    fun showLoader(show: Boolean) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowLoader(show)))
    }

    fun showAlert(title: String = "Alert", message: String? = null) {
        _uiEventsLiveData.value = Event(UiEvent.ShowAlert(title = title, message = message!!))
    }

    fun showErrorDialog(
        title: String? = "Alert",
        message: String,
        @ColorRes colorBg: Int? = R.color.purple_900,
    ) {
        _uiEventsLiveData.value = Event(
            UiEvent.ShowErrorDialog(
                title = title ?: "Alert",
                message = message,
                colorBg = colorBg ?: R.color.red_600
            )
        )
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

    fun navigateByBack() {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByBack())
    }

    fun navigateByAction(actionId: Int, bundle: Bundle? = null) {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByAction(actionId, bundle))
    }

    fun setUser(user: User) {
        _userLiveData.value = user
    }

    fun addToFavourites(addToFavouriteRequest: AddToFavouriteRequest) {

        viewModelScope.launch {
            showLoader(true)
            when (val result = baseRespository?.addToFavourite(addToFavouriteRequest)) {
                is Result.Success -> {
                    showLoader(false)
                    _isFavourite.value = result
                    if (TextUtils.equals(result.value.Result.message, "Added")) {
                        showToast("Added To Favourites")
                    }
                    if (TextUtils.equals(result.value.Result.message, "Deleted")) {
                        showToast("Removed From Favourites")
                    }
                }
                is Result.Error -> result
                is Result.Failure -> {
                    showLoader(false)
                    _isFavourite.value = result
                }
            }
        }

    }


}