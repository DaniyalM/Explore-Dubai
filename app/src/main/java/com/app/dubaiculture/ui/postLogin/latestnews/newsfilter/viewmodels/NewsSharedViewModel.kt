package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.Filter
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.toString
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class NewsSharedViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application) {

    val _filter: MutableLiveData<Event<Filter>> = MutableLiveData()
    val filter: LiveData<Event<Filter>> = _filter

    val _filterList: MutableLiveData<Event<ArrayList<Filter>>> = MutableLiveData()
    val filterList: LiveData<Event<ArrayList<Filter>>> = _filterList

    val _keyword:MutableLiveData<Event<String>> = MutableLiveData()
    val keyword:LiveData<Event<String>> = _keyword

    val _dateFrom:MutableLiveData<Event<String>> = MutableLiveData()
    val dateFrom:LiveData<Event<String>> = _dateFrom

    val _dateTo:MutableLiveData<Event<String>> = MutableLiveData()
    val dateTo:LiveData<Event<String>> = _dateTo





    fun onSearchTextChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
       _keyword.value= Event(s.toString())
    }


    fun updateFilter(filter: Filter) {
        _filter.value = Event(filter)
    }


    fun onStartDateSelection(startDate:Date){
        val format = "yyyy-MM-dd"
        val str = startDate.toString(format)
       _dateFrom.value= Event(str)
    }

    fun onEndDateSelection(startDate:Date){
        val format = "yyyy-MM-dd"
        val str = startDate.toString(format)
        _dateTo.value=Event(str)
    }




}