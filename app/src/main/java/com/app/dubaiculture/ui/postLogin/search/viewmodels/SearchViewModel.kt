package com.app.dubaiculture.ui.postLogin.search.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.search.SearchRepository
import com.app.dubaiculture.data.repository.search.local.SearchResultItem
import com.app.dubaiculture.data.repository.search.local.SearchTab
import com.app.dubaiculture.data.repository.search.remote.request.SearchPaginationRequest
import com.app.dubaiculture.data.repository.search.remote.request.SearchRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val searchRepository: SearchRepository
) : BaseViewModel(application) {

    private val _tabs: MutableLiveData<List<SearchTab>> = MutableLiveData()
    val tabs: LiveData<List<SearchTab>> = _tabs
    private val _tab: MutableLiveData<Event<SearchTab>> = MutableLiveData()
    val tab: LiveData<Event<SearchTab>> = _tab

    private val _viewFlag: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val viewFlag: LiveData<Event<Boolean>> = _viewFlag
    private val _stringList: MutableLiveData<List<String>> = MutableLiveData()
    val stringList: LiveData<List<String>> = _stringList

    private val _count: MutableLiveData<Int> = MutableLiveData()
    val count: LiveData<Int> = _count


    private var _searchFilter: MutableLiveData<Event<SearchPaginationRequest>> = MutableLiveData()
    var searchFilter: LiveData<Event<SearchPaginationRequest>> = _searchFilter

    private val _searchPaginationItem: MutableLiveData<PagingData<SearchResultItem>> =
        MutableLiveData()
    val searchPaginationItem: LiveData<PagingData<SearchResultItem>> = _searchPaginationItem

    private val context = getApplication<ApplicationEntry>()

    init {

        createTabs()
        if (!context.auth.isGuest) {
            getSearchHistory()
        } else {
            _viewFlag.value = Event(true)
        }

    }


    fun updateIsOldData(isOld: Boolean) {
        val searchRequest: SearchPaginationRequest = _searchFilter.value!!.peekContent()
        updateSearch(searchRequest.copy(isOld = isOld))
    }

    fun updateCategoryData(category: String) {
        val searchRequest: SearchPaginationRequest = _searchFilter.value!!.peekContent()
        updateSearch(searchRequest.copy(category = category))
    }

    fun updateSorting(aToz: Boolean) {
        val searchRequest: SearchPaginationRequest = _searchFilter.value!!.peekContent()
        if (aToz)
            updateSearch(searchRequest.copy(sort = "asc"))
        else
            updateSearch(searchRequest.copy(sort = "desc"))
    }

    fun updateKeyword(string: String) {
        _viewFlag.value = Event(string.isNotEmpty())
        if (string.isNotEmpty()) {
            updateSearch(SearchPaginationRequest(keyword = string))
        }
    }


    private fun updateSearch(searchRequest: SearchPaginationRequest) {
        _searchFilter.value = Event(
            searchRequest.copy(
                keyword = searchRequest.keyword,
                filter = searchRequest.filter,
                culture = context.auth.locale,
                category = searchRequest.category,
                isOld = searchRequest.isOld,
                sort = searchRequest.sort
            )
        )
    }

    fun updateTab(searchTab: SearchTab) {
        _tab.value = Event(searchTab)
    }

    fun updateList(searchTab: SearchTab) {
        val data = _tabs.value ?: return
        data.map {
            if (searchTab.id == it.id) return@map searchTab
            else return@map it.copy(isSelected = false)
        }.let {
            _tabs.value = it
        }
    }

    private fun createTabs() {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                searchRepository.getSearchHeader(
                    culture = context.auth.locale!!
                )) {
                is Result.Success -> {
                    showLoader(false)
                    _tabs.value = result.value

                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }

    }

    fun setCount(count: Int) {
        _count.value = count
    }

    fun displayError(message: String) {
        showToast(message = message)
    }

    fun getSearchHistory() {
//        showLoader(true)
        viewModelScope.launch {
            when (val result =
                searchRepository.getSearchHistory(
                    SearchRequest(
                        userId = context.auth.user!!.userId,
                        culture = context.auth.locale!!
                    )
                )) {
                is Result.Success -> {
//                    showLoader(false)
                    if (result.value.isNotEmpty()) {
                        _stringList.value = result.value
                    } else {
                        _viewFlag.value = Event(true)
                    }


                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    fun clearHistory() {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                searchRepository.clearHistory(
                    SearchRequest(
                        userId = context.auth.user!!.userId,
                        culture = context.auth.locale!!
                    )
                )) {
                is Result.Success -> {
                    showLoader(false)
                    if (result.value) {
                        getSearchHistory()
                    }

                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    fun search(
        searchRequest: SearchPaginationRequest
    ) {
        viewModelScope.launch {
            when (val result = searchRepository.fetchResults(
                searchRequest,
                {
                    setCount(it)
                }, {
                    displayError(it)
                }
            )) {
                is Result.Success -> {
                    result.value
                        .cachedIn(viewModelScope)
                        .collectLatest {
                            _searchPaginationItem.value = it
                        }
                }
                is Result.Failure -> {
                    showLoader(false)
                }


            }
        }

    }

    fun updatePagingList(tab: SearchTab) {

        val data = _searchPaginationItem.value ?: return

        if (tab.id != 0) {
            data.filter {
                tab.title.toLowerCase(Locale.ROOT) == it.type
            }.let {
                _searchPaginationItem.value = it
            }
        } else {
            _searchPaginationItem.value = data
        }


    }


//    fun onSearchTextChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
//        _viewFlag.value = Event(s.isNotEmpty())
//        if (s.isNotEmpty()) {
//            updateSearch(SearchPaginationRequest(keyword = s.toString()))
//        }
//    }


}