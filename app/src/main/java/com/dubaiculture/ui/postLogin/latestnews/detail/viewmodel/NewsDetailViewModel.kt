package com.dubaiculture.ui.postLogin.latestnews.detail.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.repository.news.NewsRepository
import com.dubaiculture.data.repository.news.local.NewsDetail
import com.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.data.Result
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.utils.Constants.NavBundles.NEW_LOCALE
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val newsRespository: NewsRepository
) : BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()


    private val _newsDetail: MutableLiveData<Event<NewsDetail>> = MutableLiveData()
    val newsDetail: LiveData<Event<NewsDetail>> = _newsDetail

    init {
        savedStateHandle.get<String>(Constants.NavBundles.NEWS_ID)?.let { id ->
            newsDetail(id, context.auth.locale.toString())
        }
    }

    fun newsDetail(id: String, locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                newsRespository.getNewsDetails(NewsRequest(id = id, culture = locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _newsDetail.value = result.value

                }
                is Result.Failure -> {
                    showLoader(false)

                }
            }
        }

    }
}