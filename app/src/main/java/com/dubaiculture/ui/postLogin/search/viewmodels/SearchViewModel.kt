package com.dubaiculture.ui.postLogin.search.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.search.SearchRepository
import com.dubaiculture.data.repository.search.local.SearchResultItem
import com.dubaiculture.data.repository.search.local.SearchTab
import com.dubaiculture.data.repository.search.remote.request.SearchPaginationRequest
import com.dubaiculture.data.repository.search.remote.request.SearchRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
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


    private var _searchFilter: MutableLiveData<Event<SearchPaginationRequest>> =
        MutableLiveData(Event(SearchPaginationRequest()))

    var searchFilter: LiveData<Event<SearchPaginationRequest>> = _searchFilter

    private val _searchPaginationItem: MutableLiveData<PagingData<SearchResultItem>> =
        MutableLiveData()
    val searchPaginationItem: LiveData<PagingData<SearchResultItem>> = _searchPaginationItem

    private val context = getApplication<ApplicationEntry>()

    init {

        createTabs()
        callHistoryIfGuest()


    }


    fun updateIsOldData(isOld: Boolean) {
        updateFilter(_searchFilter.value!!.peekContent().copy(isOld = isOld))


    }

    fun updateCategoryData(category: String) {
        updateFilter(_searchFilter.value!!.peekContent().copy(category = category))
    }

    fun updateSorting(aToz: Boolean) {
        if (aToz)
            updateFilter(_searchFilter.value!!.peekContent().copy(sort = "asc"))
        else
            updateFilter(_searchFilter.value!!.peekContent().copy(sort = "desc"))

    }


    fun updateKeyword(string: String) {
        _viewFlag.value = Event(string.isNotEmpty())
//        if (string.isNotEmpty()) {
        updateFilter(_searchFilter.value!!.peekContent().copy(keyword = string))
//        }
    }

//    private fun updateSearch(searchRequest: SearchPaginationRequest) {
//        _searchFilter.value = Event(
//            searchRequest.copy(
//                keyword = searchRequest.keyword,
//                filter = searchRequest.filter,
//                culture = context.auth.locale ?: "en",
//                category = searchRequest.category.ifEmpty { "1" },
//                isOld = searchRequest.isOld,
//                sort = searchRequest.sort
//            )
//        )
//    }

    fun updateFilter(searchRequest: SearchPaginationRequest) {
        _searchFilter.value = Event(searchRequest)
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
      //  showSnackbar(message = message)
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
                        _stringList.value = mutableListOf()
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
                        callHistoryIfGuest()
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
        showLoader(true)
        var search: SearchPaginationRequest = searchRequest ?: SearchPaginationRequest()
        if (searchRequest.category.isEmpty())
            search = searchRequest.copy(category = "0")


        viewModelScope.launch {
            when (val result = searchRepository.fetchResults(
                search,
                {
                    setCount(it)
                }, {
                    if (!_tab.value?.peekContent()?.title.isNullOrEmpty()) {
                        val tabTitle = _tab.value?.peekContent()?.title
                        if (tabTitle?.contains("All")!!) {
                            displayError(it)
                        } else {
                            _searchPaginationItem.value = PagingData.empty()
                            setCount(0)
                        }
                    }

                }
            )) {
                is Result.Success -> {
                    showLoader(false)
                    result.value
                        .cachedIn(viewModelScope)
                        .collectLatest {
                            callHistoryIfGuest()
                            _searchPaginationItem.value = it
                        }
                }
                is Result.Failure -> {
                    showLoader(false)
                }


            }
        }

    }

    private fun callHistoryIfGuest() {
        if (!context.auth.isGuest) {
            getSearchHistory()
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