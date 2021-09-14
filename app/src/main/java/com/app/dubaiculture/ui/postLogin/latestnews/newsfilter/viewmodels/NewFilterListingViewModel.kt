package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.news.NewsRepository
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.remote.request.NewsFilterRequest
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.Filter
import com.app.dubaiculture.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFilterListingViewModel @Inject constructor(
    application: Application,
    private val newsRepository: NewsRepository
):BaseViewModel(application) {
    private val _news: MutableLiveData<List<LatestNews>> = MutableLiveData()
    val news: LiveData<List<LatestNews>> = _news
    private val context = getApplication<ApplicationEntry>()


    init {
        getFilterNews(Filter())
    }


    fun getFilterNews(
        filter:Filter
    ) {
//        showLoader(true)
            viewModelScope.launch {
                when (val result = newsRepository.getFilterNews(
                    NewsFilterRequest(
                        culture = context.auth.locale ?: "en",
                        dateTo = filter.dateTo?:"",
                        dateFrom = filter.dateFrom?:"",
                        keyword = filter.keyword?:"",
                        tags = filter.tags
                    )
                )) {
                    is Result.Success -> {
//                        showLoader(false)
                        _news.value = result.value
                    }
                    is Result.Failure ->showAlert(result.errorMessage.toString())
                    is Result.Error ->showAlert(result.exception.message.toString())

                }
            }


    }



}