package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.local.*
import com.app.dubaiculture.data.repository.trip.remote.request.EventAttractionRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripSharedViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()

    val _showPlan: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val showPlan: LiveData<Event<Boolean>> = _showPlan

    val _duration: MutableLiveData<List<Duration>> = MutableLiveData()
    val duration: LiveData<List<Duration>> = _duration

    val _durationSummary: MutableLiveData<List<Duration>> = MutableLiveData()
    val durationSummary: LiveData<List<Duration>> = _durationSummary

    val _dates: MutableLiveData<List<Duration>> = MutableLiveData()
    val dates: LiveData<List<Duration>> = _dates

    val _type: MutableLiveData<LocationNearest> = MutableLiveData()
    val type: LiveData<LocationNearest> = _type

    val _nearestLocationType: MutableLiveData<List<LocationNearest>> = MutableLiveData()
    val nearestLocation: LiveData<List<LocationNearest>> = _nearestLocationType

    val _interestedInList: MutableLiveData<List<InterestedInType>> = MutableLiveData()
    val interestedInList: LiveData<List<InterestedInType>> = _interestedInList

    val _eventAttraction: MutableLiveData<Event<EventAttractionRequest>> = MutableLiveData()
    val eventAttraction: LiveData<Event<EventAttractionRequest>> = _eventAttraction

    val _eventAttractionResponse: MutableLiveData<EventAttractions> = MutableLiveData()
    val eventAttractionResponse: LiveData<EventAttractions> = _eventAttractionResponse

    fun updateLocationItem(nearestLocation: LocationNearest) {
        _type.value = nearestLocation
    }

    fun updateInLocationList(nearestLocation: LocationNearest) {

        val data = _nearestLocationType.value ?: return
        val updateData = updateToDefault(data)
        updateData.map {
            if (nearestLocation.locationId == it.locationId
            ) return@map nearestLocation
            else {
                return@map it
            }
        }.let {

            _nearestLocationType.value = it
        }

    }

    private fun updateToDefault(data: List<LocationNearest>): List<LocationNearest> {
        return data.map {
            it.copy(isChecked = false)
        }
    }

    fun updateDurationList(duration: Duration) {

        val data = _duration.value ?: return

        data.map {
            if (duration.id == it.id
            ) return@map duration
            else return@map it
        }.let {
            _duration.value = it
        }

    }


    fun getDaysList(startDay: String, endDay: String) {

        var daysList: MutableList<String> = mutableListOf<String>()
        var updateDate = startDay
        daysList.add(updateDate)
        while (updateDate != endDay) {

            val sdf = SimpleDateFormat("dd MMM,yy")
            val c: Calendar = Calendar.getInstance()
            c.time = sdf.parse(updateDate)
            c.add(Calendar.DATE, 1) // number of days to add

            updateDate = sdf.format(c.time)
            daysList.add(updateDate)

        }

        populateList(daysList = daysList)
    }

    private fun populateList(daysList: List<String>) {

        val durationList = mutableListOf<Duration>()

        daysList.forEachIndexed { index, day ->
            durationList += Duration(
                id = index + 1,
                dayDate = day,
                hour = "1 Hour",
                isDay = 0,
                isSelected = false
            )

        }

        _duration.value = durationList


    }

    fun getList(days: Int) {

        var daysList: MutableList<String> = mutableListOf<String>()
        val sdf = SimpleDateFormat("dd MMM,yy")
        var currentDate = sdf.format(Date())
        for (i in 1 until days + 1) {
            val c: Calendar = Calendar.getInstance()
            c.time = sdf.parse(currentDate)
            c.add(Calendar.DATE, 1) // number of days to add

            currentDate = sdf.format(c.time)
            daysList.add(currentDate)
        }

        populateList(daysList)

    }

    fun repeatToAll(repeatToAll: Boolean, duration: Duration) {

        if (repeatToAll) {

            val daysList: List<Duration> = _duration.value ?: return

            daysList.map {

                return@map it.copy(
                    dayDate = it.dayDate,
                    hour = duration.hour,
                    isDay = duration.isDay,
                    id = it.id,
                    isSelected = duration.isSelected
                )
            }.let {
                _duration.value = it
            }


        }

    }

    fun selectedDelete() {

        val durations: List<Duration> = _duration.value ?: return

        durations.filter { duration ->
            !duration.isSelected
        }.let {
            _duration.value = it
        }

    }

    fun postEventAttraction() {

        val eventAttractionRequest: EventAttractionRequest = EventAttractionRequest(
            category = getCategories(),
            culture = context.auth.locale.toString(),
            date = getDates(),
            location = _type.value?.locationId!!
        )
        _eventAttraction.value = Event(eventAttractionRequest)
    }

    private fun getDates(): List<String> {

        val durations: List<Duration> = _durationSummary.value ?: return emptyList()
        val input = SimpleDateFormat("dd MMM,yy")
        val output = SimpleDateFormat("yyyy-MM-dd")
        return durations.map {
            output.format(input.parse(it.dayDate))
        }

    }

    private fun getCategories(): List<String> {

        val interestedInList: List<InterestedInType> = _interestedInList.value ?: return emptyList()

        interestedInList.filter { interestedIn ->
            interestedIn.checked
        }.let {
            return it.map { it.id }
        }

    }

    fun setDates() {

        val dateList: List<Duration> = _durationSummary.value ?: return

        dateList.map {
            return@map it.copy(
                isSelected = false
            )
        }.let {
            _dates.value = it
        }

    }

}