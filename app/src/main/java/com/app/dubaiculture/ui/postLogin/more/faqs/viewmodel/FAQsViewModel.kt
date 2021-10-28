package com.app.dubaiculture.ui.postLogin.more.faqs.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.more.MoreRepository
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


    private val _faqs: MutableLiveData<List<FaqItem>> = MutableLiveData()

    private val _faqsTitle: MutableLiveData<String> = MutableLiveData()
    val faqsTitle: LiveData<String> = _faqsTitle

    private val _searchfaqs: MutableLiveData<List<FaqItem>> = MutableLiveData()
    val faqs: LiveData<List<FaqItem>> = _searchfaqs

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
                    _faqs.value = result.value.faqItems
                    _faqsTitle.value = result.value.faqTitle
                    _searchfaqs.value = result.value.faqItems
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    fun setFaqs(faqs: List<FaqItem>) {
        _faqs.value = faqs
    }

    fun updateFaq(faqItem: FaqItem) {
        _faq.value = Event(faqItem)
    }

    fun updateFaqInList(faqItem: FaqItem) {
        val data = _searchfaqs.value ?: return
        data.map {
            if (faqItem.id == it.id
            ) return@map faqItem
            else return@map it
        }.let {
            _searchfaqs.value = it
        }
    }

    fun searchFaq(keyword: String) {
        if (keyword.isNotEmpty()) {
            val data = _searchfaqs.value ?: return
            data.filter {
                keyword in it.question
            }.let {
                _searchfaqs.value = it
            }
        } else {
            _searchfaqs.value = _faqs.value
        }
    }

    fun onSearchTextChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        searchFaq(s.toString())
    }

}
