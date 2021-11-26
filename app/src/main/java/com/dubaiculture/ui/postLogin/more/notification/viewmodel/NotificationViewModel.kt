package com.dubaiculture.ui.postLogin.more.notification.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.more.MoreRepository
import com.dubaiculture.data.repository.more.remote.response.notification.NotificationRequest
import com.dubaiculture.data.repository.more.remote.response.notification.Notifications
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    application: Application,
    private val moreRepository: MoreRepository
) :
    BaseViewModel(application) {


    private val _notificationPagination: MutableLiveData<PagingData<Notifications>> =
        MutableLiveData()
    val notificationPagination: LiveData<PagingData<Notifications>> = _notificationPagination
    private val context = getApplication<ApplicationEntry>()
    private val _count: MutableLiveData<Event<Int>> = MutableLiveData()
    val count: LiveData<Event<Int>> = _count

    init {

        getNotification(context.auth.locale.toString())
    }

    private fun setCount(count: Int) {
        _count.value = Event(count)
    }

    private fun getNotification(locale: String) {
        viewModelScope.launch {
            showLoader(true)
            val result = moreRepository.getNotification(NotificationRequest(culture = locale)) {
                setCount(
                    it
                )
            }
            if (result is Result.Success) {
                showLoader(false)

                result.value
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _notificationPagination.value = it
                    }
            } else if (result is Result.Failure) {
                showLoader(false)
                showAlert(result.errorMessage.toString())

            }
        }
    }
}