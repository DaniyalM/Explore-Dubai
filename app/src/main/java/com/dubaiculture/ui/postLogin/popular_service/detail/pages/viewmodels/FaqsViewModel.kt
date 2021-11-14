package com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dubaiculture.data.repository.more.MoreRepository
import com.dubaiculture.data.repository.more.local.FaqItem
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqsViewModel @Inject constructor(
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
    fun setFaqs(faqs: List<FaqItem>) {
        _searchfaqs.value = faqs
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

}