package com.dubaiculture.ui.postLogin.more.notification.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.more.MoreRepository
import com.dubaiculture.data.repository.more.remote.response.notification.NotificationRequest
import com.dubaiculture.data.repository.more.remote.response.notification.Notifications
import com.dubaiculture.data.repository.news.local.LatestNews
import com.dubaiculture.data.repository.notification.NotificationModel
import com.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(application: Application , private val moreRepository: MoreRepository) :
    BaseViewModel(application) {


    private val _notificationPagination: MutableLiveData<PagingData<Notifications>> = MutableLiveData()
    val notificationPagination: LiveData<PagingData<Notifications>> = _notificationPagination

    init {

        getNotification()
    }

    private  fun getNotification(){
            viewModelScope.launch {
                showLoader(true)
                val result = moreRepository.getNotification(NotificationRequest())
                if(result is Result.Success){
                    showLoader(false)

                    result.value
                        .cachedIn(viewModelScope)
                        .collectLatest {
                            _notificationPagination.value = it
                        }
                }else if(result is Result.Failure){
                    showLoader(false)
                    showAlert(result.errorMessage.toString())

                }
            }
        }
}