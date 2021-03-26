package com.app.dubaiculture.ui.postLogin.events.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.EventRepository
import com.app.dubaiculture.data.repository.event.local.models.EventFilterData
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import com.app.dubaiculture.data.repository.filter.models.SelectedItems
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.GpsStatusListener
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventViewModel @ViewModelInject constructor(
    application: Application,
    private val eventRepository: EventRepository,
) : BaseViewModel(application,eventRepository) {


    var keyword: ObservableField<String> = ObservableField("")
    var location: ObservableField<String> = ObservableField("")
    var dateFrom: ObservableField<String> = ObservableField("")
    var dateTo: ObservableField<String> = ObservableField("")
    var category: ObservableField<String> = ObservableField("")
    var type: ObservableField<String> = ObservableField("")

    val showAndHide = MutableLiveData<Boolean?>(true)


    private var categoryList = mutableListOf<Filter>()

    val gpsStatusLiveData = GpsStatusListener(application)
    private val _addToFavourite: MutableLiveData<Result<AddToFavouriteResponse>> = MutableLiveData()
    val addToFavourite: LiveData<Result<AddToFavouriteResponse>> = _addToFavourite
    private val _eventCategoryList: MutableLiveData<Result<EventHomeListing>> =
        MutableLiveData()
    val eventCategoryList: LiveData<Result<EventHomeListing>> =
        _eventCategoryList
    private val _eventDetailList: MutableLiveData<Result<Events>> = MutableLiveData()
    val eventDetail: LiveData<Result<Events>> = _eventDetailList

    private val _eventList: MutableLiveData<Result<List<Events>>> = MutableLiveData()
    val eventfilterRequest: LiveData<Result<List<Events>>> = _eventList


    private val _filterList: MutableLiveData<Result<EventFilterData>> = MutableLiveData()
    val filterList: LiveData<Result<EventFilterData>> = _filterList


//    val _modelForSearching : MutableLiveData<ArrayList<EventRequest>> = MutableLiveData()
//    val modelForSearching : LiveData<ArrayList<EventRequest>> =_modelForSearching
//    val _filterDataList : MutableLiveData<Event<ArrayList<SelectedItems>>> = MutableLiveData()
//    val filterDataList: LiveData<Event<ArrayList<SelectedItems>>> = _filterDataList


    val _filterDataList: MutableLiveData<ArrayList<SelectedItems>> = MutableLiveData()
    val filterDataList: LiveData<ArrayList<SelectedItems>> = _filterDataList


    fun addToFavourite(userId: String, itemId: String, type: Int = 2) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.addToFavourite(AddToFavouriteRequest(
                userId = userId,
                itemId = itemId,
                type = type
            ))) {
                is Result.Success -> {
                    showLoader(false)
                    _addToFavourite.value = result
                }
                is Result.Failure -> {
                    showLoader(false)
                    _addToFavourite.value = result
                }

            }
        }
    }


    fun getEventHomeToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                eventRepository.fetchHomeEvents(EventRequest(culture = locale))) {
                is Result.Success -> {
                    _eventCategoryList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)
                    _eventCategoryList.value = result
                }
            }
        }
    }

    fun getDataFilterBtmSheet(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                eventRepository.fetchDataFilterBtmSheet(EventRequest(culture = locale))) {
                is Result.Success -> {
                    _filterList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }

    fun getEventDetailsToScreen(eventId: String, locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.fetchEvent(EventRequest(
                eventId = eventId,
                culture = locale))) {
                is Result.Success -> {
                    _eventDetailList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _eventDetailList.value = result
                    showLoader(false)
                }
            }
        }
    }

    fun getFilterEventList(eventRequest: EventRequest) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = eventRepository.fetchEventsbyFilters(eventRequest)) {
                is Result.Success -> {
                    _eventList.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _eventList.value = result
                    showLoader(false)
                }
            }
        }
    }


    fun getNearEvents(list: List<Events>): List<Events> =
        list.filter {
            it.latitude != "" && it.longitude != ""
        }


    fun updateHeaderItems(position: Int = 0) {
        when (position) {
            0 -> {
                getFilterEventList(EventRequest(culture = "en"))
            }
            1 -> {
                getFilterEventList(EventRequest(culture = "en",
                    dateFrom = getWeek().first(),
                    dateTo = getWeek().last()))
            }
            2 -> {
                getFilterEventList(EventRequest(culture = "en"))
            }
            3 -> {
                getFilterEventList(EventRequest(culture = "en",
                    dateFrom = getNextSevenDays().first(),
                    dateTo = getNextSevenDays().last()))
            }
        }
    }

    fun getWeekend(list: List<Events>): MutableList<Events> {
        val weekendList = list.filter {
            it.fromDay == "Friday" || it.fromDay == "Saturday"
        }
        return weekendList as MutableList<Events>
    }

    private fun getWeek(): Array<String?> {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
//        val date = getDateObj("2021-03-28T17:19:00")
//        calendar.time = date
        val days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            days[i] = format.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            Timber.e("Week ${days[i]}")
        }
        return days
    }

    private fun getNextSevenDays(): Array<String?> {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
//        val date = getDateObj("2021-03-28T17:19:00")
//        calendar.time = date
        val next7Days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            next7Days[i] = format.format(calendar.time)
            calendar.add(Calendar.DATE, 1)

            Timber.e("${next7Days[i]}")
        }
        return next7Days
    }
}