package com.dubaiculture.ui.base

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
import androidx.navigation.NavOptions
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import com.dubaiculture.data.repository.user.local.User
import com.dubaiculture.data.repository.user.local.guest.Guest
import com.dubaiculture.ui.preLogin.PreLoginActivity
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.GpsStatusListener
import com.dubaiculture.utils.event.Event
import com.dubaiculture.utils.event.UiEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
    protected val _userGuestLiveData = MutableLiveData<Guest>()
    val userGuestLiveData: LiveData<Guest> = _userGuestLiveData


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


    fun navigateByBack() {
        _uiEventsLiveData.value = Event(UiEvent.NavigateBack())
    }


    fun setUser(user: User) {
        _userLiveData.value = user
    }

    fun setGuestUser(user: Guest) {
        _userGuestLiveData.value = user
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


    fun showAlert(
        message: String,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionPositive: (() -> Unit)? = null
    ) {
        _uiEventsLiveData.value = Event(
            UiEvent.ShowAlert(
                title = title,
                message = message,
                textPositive = textPositive,
                textNegative = textNegative,
                actionPositive = actionPositive
            )
        )
    }

    fun showToast(message: String) {
        _uiEventsLiveData.value = Event(UiEvent.ShowToast(message))
    }

    fun showToast(resourceId: Int) {
        _uiEventsLiveData.value = Event(UiEvent.ShowToastByRId(resourceId))
    }

    fun showSnackbar(message: String, action: (() -> Unit)? = null) {
        _uiEventsLiveData.value = Event(UiEvent.ShowSnackbar(message, action))
    }

    fun navigateByDirections(navDirections: NavDirections) {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByDirections(navDirections))
    }

    //TODO Ammar: use nav options by nav graph
    fun navigateByActionNavOption(actionId: Int, bundle: Bundle? = null, navOptions: NavOptions) {
        _uiEventsLiveData.value =
            Event(UiEvent.NavigateByActionNavOption(actionId, bundle, navOptions))
    }

    fun navigateByAction(actionId: Int, bundle: Bundle? = null) {
        _uiEventsLiveData.value = Event(UiEvent.NavigateByAction(actionId, bundle))
    }

    fun showBottomSheet(
        bottomSheetFragment: BottomSheetDialogFragment,
        tag: String? = null
    ) {
        _uiEventsLiveData.value = Event(UiEvent.ShowBottomSheet(bottomSheetFragment, tag))

    }


}