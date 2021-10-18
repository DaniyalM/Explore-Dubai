package com.app.dubaiculture.ui.postLogin.more.faqs.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.more.MoreRepository
import com.app.dubaiculture.data.repository.more.local.FAQs
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
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
    private val context = getApplication<ApplicationEntry>()


    private val _faqs: MutableLiveData<FAQs> = MutableLiveData()
    val faqs: LiveData<FAQs> = _faqs

    private val _faq: MutableLiveData<Event<FaqItem>> = MutableLiveData()
    val faq: LiveData<Event<FaqItem>> = _faq

    init {
        faqs(context.auth.locale.toString())
    }

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

    fun updateFaq(faqItem: FaqItem){
        _faq.value = Event(faqItem)
    }
    fun updateFaqInList(faqItem: FaqItem) {
        val data = _faqs.value ?: return
        data.faqItems.
            map {
                if (faqItem.id == it.id
                ) return@map faqItem
                else return@map it
            }.let {
                _faqs.value?.faqItems= it
            }
    }

}
