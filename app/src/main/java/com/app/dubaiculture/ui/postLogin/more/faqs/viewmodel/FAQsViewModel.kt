package com.app.dubaiculture.ui.postLogin.more.faqs.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.more.MoreRepository
import com.app.dubaiculture.data.repository.more.local.FAQs
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FAQsViewModel @Inject constructor(
    application: Application,
    val moreRepository: MoreRepository
) : BaseViewModel(application) {


    private val _faqs: MutableLiveData<Event<FAQs>> = MutableLiveData()
    val faqs: LiveData<Event<FAQs>> = _faqs
    fun faqs(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = moreRepository.getFaqs(PrivacyAndTermRequest(locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _faqs.value = result.value
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }
}
