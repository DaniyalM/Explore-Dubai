package com.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.news.NewsRepository
import com.dubaiculture.data.repository.news.local.LatestNews
import com.dubaiculture.data.repository.news.remote.request.NewsFilterRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFilterListingViewModel @Inject constructor(
    application: Application,
    private val newsRepository: NewsRepository
) : BaseViewModel(application) {
    private val _news: MutableLiveData<List<LatestNews>> = MutableLiveData()
    val news: LiveData<List<LatestNews>> = _news


    private val context = getApplication<ApplicationEntry>()


    init {
        getFilterNews(Filter())
    }


    fun getFilterNews(
        filter: Filter
    ) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = newsRepository.getFilterNews(
                NewsFilterRequest(
                    culture = context.auth.locale ?: "en",
                    dateTo = filter.dateTo,
                    dateFrom = filter.dateFrom,
                    keyword = filter.keyword,
                    tags = filter.tags
                )
            )) {
                is Result.Success -> {
                    showLoader(false)
                    _news.value = result.value

                }
                is Result.Failure -> showToast(result.errorMessage.toString())
                is Result.Error -> showAlert(result.exception.message.toString())

            }
        }


    }


}