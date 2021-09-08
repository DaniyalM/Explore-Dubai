package com.app.dubaiculture.ui.postLogin.latestnews.detail.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.repository.news.NewsRepository
import com.app.dubaiculture.data.repository.news.local.NewsDetail
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    application: Application,
    val newsRepository: NewsRepository
) : BaseViewModel(application) {


    private val _newsDetail: MutableLiveData<Event<NewsDetail>> = MutableLiveData()
    val newsDetail: LiveData<Event<NewsDetail>> = _newsDetail


    fun newsDetail(id: String, locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                newsRepository.getNewsDetails(NewsRequest(id = id, culture = locale))) {
                is com.app.dubaiculture.data.Result.Success -> {
                    showLoader(false)
                    _newsDetail.value = result.value

                }
                is com.app.dubaiculture.data.Result.Failure -> {
                    showLoader(false)

                }
            }
        }

    }
}