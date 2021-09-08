package com.app.dubaiculture.ui.postLogin.latestnews.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.news.NewsRepository
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.NEW_LOCALE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val newsRespository: NewsRepository
) : BaseViewModel(application) {


    private val _newsLatest: MutableLiveData<List<LatestNews>> = MutableLiveData()
    val newsLatest: LiveData<List<LatestNews>> = _newsLatest

    private val _newsPagination: MutableLiveData<PagingData<LatestNews>> = MutableLiveData()
    val newsPagination: LiveData<PagingData<LatestNews>> = _newsPagination

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(NEW_LOCALE)?.let {
                getNews(it)
                getLatestNews(it)
            }
        }
    }

    suspend fun refreshDiscussions() {
        _newsPagination.value = PagingData.empty()
        savedStateHandle.get<String>(NEW_LOCALE)?.let {
            viewModelScope.launch {
                getNews(savedStateHandle.get<String>(NEW_LOCALE))
                getLatestNews(savedStateHandle.get<String>(NEW_LOCALE))
            }
        }

    }

    fun getLatestNews(culture: String? = "en") {
        viewModelScope.launch {
            when (val result = newsRespository.getLatestNews(
                NewsRequest(
                    culture = culture!!
                )
            )) {
                is Result.Success -> {
                    _newsLatest.value = result.value
                }
                is Result.Failure -> result
                is Result.Error -> result
            }
        }
    }

    suspend fun getNews(culture: String? = "en") {
        val result = newsRespository.getNews(NewsRequest(culture = culture!!))
        showLoader(false)
        when (result) {
            is Result.Success -> {
                result.value
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _newsPagination.value = it
                    }
            }
            is Result.Failure -> {
                showAlert(result.errorMessage.toString())
            }
        }

    }


}