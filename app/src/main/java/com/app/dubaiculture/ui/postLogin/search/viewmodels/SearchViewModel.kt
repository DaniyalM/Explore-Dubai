package com.app.dubaiculture.ui.postLogin.search.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {

    private var _viewFlag: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    var viewFlag: LiveData<Event<Boolean>> = _viewFlag
    private var _stringList: MutableLiveData<List<String>> = MutableLiveData()
    var stringList: LiveData<List<String>> = _stringList

    private fun search(keyword: String) {

    }

    fun onSearchTextChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        _viewFlag.value = Event(s.isNotEmpty())
        search(s.toString())
    }
}