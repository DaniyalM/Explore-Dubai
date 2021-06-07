package com.app.dubaiculture.ui.postLogin.latestnews.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.news.NewsRepository
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.local.News
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(application: Application,
                                                 private val newsRespository: NewsRepository) : BaseViewModel(application) {


    private val _news: MutableLiveData<Event<News>> = MutableLiveData()
    val news: LiveData<Event<News>> = _news

    fun getNews(newsRequest: NewsRequest) {
        viewModelScope.launch {
            when (val result = newsRespository.getLatestNews(newsRequest)) {
                is Result.Success -> {
                    _news.value = result.value
                }
                is Result.Failure -> result
                is Result.Error -> result
            }
        }
    }


}