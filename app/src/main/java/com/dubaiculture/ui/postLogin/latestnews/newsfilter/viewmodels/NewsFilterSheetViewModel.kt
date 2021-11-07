package com.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.news.NewsRepository
import com.dubaiculture.data.repository.news.local.NewsTags
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFilterSheetViewModel @Inject constructor(
    application: Application,
    private val newsRepository: NewsRepository
) : BaseViewModel(application) {
    private val _newsTags: MutableLiveData<List<NewsTags>> = MutableLiveData()
    val newsTags: LiveData<List<NewsTags>> = _newsTags
    private val context = getApplication<ApplicationEntry>()

    init {
        getTags()
    }

    private fun getTags() {
        showLoader(true)
        viewModelScope.launch {
            when (val result = newsRepository.getNewsTags(
                culture = context.auth.locale.toString()
            )) {

                is Result.Success -> {
                    showLoader(false)
                    _newsTags.value = result.value
                }
                is Result.Failure -> {
                    showLoader(false)
                    showAlert(result.errorMessage.toString())
                }
                is Result.Error -> showAlert(result.exception.message.toString())

            }
        }
    }

    fun updateTags() {
        val data = _newsTags.value ?: return
        data.map {
            if (it.isSelected)
                return@map it.copy(isSelected = false)
            else
                return@map it
        }.let {
            _newsTags.value = it
        }
    }

}