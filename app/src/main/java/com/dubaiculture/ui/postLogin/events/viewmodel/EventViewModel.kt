package com.dubaiculture.ui.postLogin.events.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.event.EventRepository
import com.dubaiculture.data.repository.event.local.models.EventFilterData
import com.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.event.local.models.Filter
import com.dubaiculture.data.repository.event.remote.request.EventRequest
import com.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import com.dubaiculture.data.repository.filter.models.SelectedItems
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.dateFormatEn
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    application: Application,
    private val eventRepository: EventRepository,
) : BaseViewModel(application, eventRepository) {
    private val context = getApplication<ApplicationEntry>()

    var keyword: ObservableField<String> = ObservableField("")
    var location: ObservableField<String> = ObservableField("")
    var dateFrom: ObservableField<String> = ObservableField("")
    var dateTo: ObservableField<String> = ObservableField("")
    var category: ObservableField<String> = ObservableField("")
    var type: ObservableField<String> = ObservableField("")

    val keywordState = MutableLiveData("")
    val locationState = MutableLiveData("")
    val dateFrmState = MutableLiveData("")
    val dateToState = MutableLiveData("")
    val radioBtnState = MutableLiveData("")


    private var categoryList = mutableListOf<Filter>()


    private val _addToFavourite: MutableLiveData<Result<AddToFavouriteResponse>> = MutableLiveData()
    val addToFavourite: LiveData<Result<AddToFavouriteResponse>> = _addToFavourite
    private val _eventCategoryList: MutableLiveData<Result<EventHomeListing>> =
        MutableLiveData()
    val eventCategoryList: LiveData<Result<EventHomeListing>> =
        _eventCategoryList

    //    private val _eventDetailList: MutableLiveData<Result<ScheduleData>> = MutableLiveData()
//    val eventDetail: LiveData<Result<ScheduleData>> = _eventDetailList
    private val _eventDetail: MutableLiveData<Result<Events>> = MutableLiveData()
    val eventDetail: LiveData<Result<Events>> = _eventDetail

    private val _eventList: MutableLiveData<Result<List<Events>>> = MutableLiveData()
    val eventfilterRequest: LiveData<Result<List<Events>>> = _eventList


    private val _filterList: MutableLiveData<Result<EventFilterData>> = MutableLiveData()
    val filterList: LiveData<Result<EventFilterData>> = _filterList

    val _searchBarKeyWord: MutableLiveData<Event<String>> = MutableLiveData()
    val searchBarKeyWord: LiveData<Event<String>> = _searchBarKeyWord


    val _filterDataList: MutableLiveData<ArrayList<SelectedItems>> = MutableLiveData()
    val filterDataList: LiveData<ArrayList<SelectedItems>> = _filterDataList


    init {
        getEventHomeToScreen(context.auth.locale.toString())
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
            when (val result = eventRepository.fetchDetailEvent(
                EventRequest(
                    eventId = eventId,
                    culture = locale
                )
            )) {
                is Result.Success -> {
                    _eventDetail.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    _eventDetail.value = result
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


    fun updateHeaderItems(position: Int = 0) {
        when (position) {
            0 -> {
                getFilterEventList(EventRequest(culture = context.auth.locale.toString()))
            }
            1 -> {
                getFilterEventList(
                    EventRequest(
                        culture = context.auth.locale.toString(),
                        dateFrom = dateFormatEn(getWeek().first()),
                        dateTo = dateFormatEn(getWeek().last())
                    )
                )
            }
            2 -> {
                getFilterEventList(EventRequest(culture = context.auth.locale.toString()))
            }
            3 -> {
                getFilterEventList(
                    EventRequest(
                        culture = context.auth.locale.toString(),
                        dateFrom = dateFormatEn(getNextSevenDays().first()),
                        dateTo = dateFormatEn(getNextSevenDays().last())
                    )
                )
            }
            4 -> {
                getFilterEventList(
                    EventRequest(
                        culture = context.auth.locale.toString(),
                        dateFrom = dateFormatEn(getThirtyDays().first()),
                        dateTo = dateFormatEn(getThirtyDays().last())
                    )
                )
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
        val calendar = Calendar.getInstance(Locale.ENGLISH)
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
        val calendar = Calendar.getInstance(Locale.ENGLISH)
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

    private fun getThirtyDays(): Array<String?> {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance(Locale.ENGLISH)
//        val date = getDateObj("2021-03-28T17:19:00")
//        calendar.time = date
        val next30Days = arrayOfNulls<String>(30)
        for (i in 0..29) {
            next30Days[i] = format.format(calendar.time)
            calendar.add(Calendar.DATE, 1)

            Timber.e("${next30Days[i]}")
        }
        return next30Days
    }


}