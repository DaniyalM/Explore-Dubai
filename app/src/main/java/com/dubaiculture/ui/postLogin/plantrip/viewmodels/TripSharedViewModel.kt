package com.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.dubaiculture.data.repository.trip.TripRepository
import com.dubaiculture.data.repository.trip.local.*
import com.dubaiculture.data.repository.trip.remote.request.EventAttractionRequest
import com.dubaiculture.data.repository.trip.remote.response.DistanceMatrixResponse
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import com.dubaiculture.utils.location.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripSharedViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository,
    private val locationHelper: LocationHelper
) : BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()

    val _showPlan: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val showPlan: LiveData<Event<Boolean>> = _showPlan

    val _showSave: MutableLiveData<Boolean> = MutableLiveData(true)
    val showSave: LiveData<Boolean> = _showSave

    val _duration: MutableLiveData<List<Duration>> = MutableLiveData()
    val duration: LiveData<List<Duration>> = _duration

    private val _addDurationList: MutableLiveData<Durations> = MutableLiveData()
    val addDurationList: LiveData<Durations> = _addDurationList

    fun addDurations(list: Durations) {
        _addDurationList.value = list
    }

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

    val _eventAttractionList: MutableLiveData<List<EventsAndAttraction>> = MutableLiveData()
    val eventAttractionList: LiveData<List<EventsAndAttraction>> = _eventAttractionList

    val _tripList: MutableLiveData<List<EventsAndAttraction>> = MutableLiveData()
    val tripList: LiveData<List<EventsAndAttraction>> = _tripList

    val _trip: MutableLiveData<Event<String>> = MutableLiveData()
    val trip: LiveData<Event<String>> = _trip

    val _travelMode: MutableLiveData<String> = MutableLiveData(Constants.TRAVEL_MODE.DRIVING)
    val travelMode: LiveData<String> = _travelMode

    fun updateTripItem(trip: String) {
        _trip.value = Event(trip)
    }

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
                isDay = 1,
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

        val durationList = mutableListOf<Duration>()

        daysList.forEachIndexed { index, day ->
            durationList += Duration(
                id = index + 1,
                dayDate = day,
                hour = "24 Hour",
                isDay = 1,
                isSelected = false
            )

        }

        _durationSummary.value = durationList

//        populateList(daysList)

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
            date = getDatesFormat("dd MMM,yy", "yyyy-MM-dd"),
            location = _type.value?.locationId ?: "",
            save = true,
            customLatitude = if (_type.value?.locationId!!.isEmpty()) _type.value?.latitude!! else "",
            customLongitude = if (_type.value?.locationId!!.isEmpty()) _type.value?.longitude!! else ""
        )
        _eventAttraction.value = Event(eventAttractionRequest)
    }

    private fun getDatesFormat(inputFormat: String, outputFormat: String): List<String> {

        val durations: List<Duration> = _durationSummary.value ?: return emptyList()
        val input = SimpleDateFormat(inputFormat)
        val output = SimpleDateFormat(outputFormat)
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


    fun updateDate(duration: Duration) {

        val data = _dates.value ?: return
        val updateData = data.map { it.copy(isSelected = false) }
        updateData.map {
            if (duration.id == it.id
            ) return@map duration
            else {
                return@map it
            }
        }.let {
            _dates.value = it
        }


    }

    fun filterEvents(duration: Duration) {


        val eventList: List<EventsAndAttraction> =
            _eventAttractionResponse.value!!.eventsAndAttractions ?: return
//|| (output.format(input.parse(event.dateFrom)) == duration.dayDate)

        eventList.filter {
            (it.latitude != "" && it.longitude != "")
        }.let {
            it.filter { event ->
                checkEvents(event, duration)
            }.let {
                _eventAttractionList.value = it
            }
        }


    }

    private fun checkEvents(event: EventsAndAttraction, duration: Duration): Boolean {

        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("dd MMMM,yyyy")

        return if (event.isAttraction) {
            getTimeCheck(event.timeFrom, event.timeTo, duration)
        } else {
            (output.format(input.parse(event.dateFrom)) == duration.dayDate) &&
                    getTimeCheck(event.timeFrom, event.timeTo, duration)
        }

    }

    fun filterLatLong() {
        val data = _eventAttractionList.value ?: return
        data.filter {
            (it.latitude != "0.0" && it.longitude != "0.0")
        }.let {
            _eventAttractionList.value = it
        }

    }

    fun updateLocalDistance(location: Location) {
        // filterLatLong()
        val data = _eventAttractionList.value ?: return
        data.map {

            val distance = locationHelper.distance(
                location.latitude, location.longitude,
                it.latitude.ifEmpty { "40.7128" }.toDouble(),
                it.longitude.ifEmpty { "73.935242" }.toDouble()
            )
            if (it.distanceRadius == 0.0) {
                return@map it.copy(distanceRadius = distance)
            } else return@map it
        }.let {
            it.filter {
                (it.distanceRadius < 11.0 && it.longitude != "0.0" && it.latitude != "0.0")
            }.let {
                _eventAttractionList.value = it
            }
        }
    }

    private fun getTimeCheck(time: String, endTime: String, duration: Duration): Boolean {
        if (time.isNotEmpty() && endTime.isNotEmpty()) {

            val pattern = "h:mm a"
            val patternTime = "h:mmaa"
            val sdf = SimpleDateFormat(pattern)
            val sdfTime = SimpleDateFormat(patternTime)
            val outputFormat = SimpleDateFormat("HH:mm")
            var startTime = ""
            when (duration.isDay) {
                1 -> {
                    startTime = "6:00 AM"
                }
                2 -> {
                    startTime = "6:00 PM"
                }
            }

            val date = sdf.parse(startTime)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(
                Calendar.HOUR,
                Integer.parseInt(duration.hour.substring(0, duration.hour.indexOf(" "))) - 1
            )
            val endT = calendar.time
            val startT = outputFormat.format(sdf.parse(startTime))
            val stime = outputFormat.format(sdfTime.parse(time))
            val etime = outputFormat.format(sdfTime.parse(endTime))

            val stt = outputFormat.parse(startT)
            val st = outputFormat.parse(stime)
            val et = outputFormat.parse(etime)

            return ((st.after(stt) && st.before(endT)) || (et.after(stt) && et.before(
                endT
            ))) || ((stt.after(st) && endT.before(et)))

        } else {
            return false
        }

    }

    fun setDates() {

        val input = SimpleDateFormat("dd MMM,yy")
        val output = SimpleDateFormat("dd MMMM,yyyy")

        val dateList: List<Duration> = _durationSummary.value ?: return

        dateList.map {
            if (it.id == 1) {
                return@map it.copy(
                    isSelected = true,
                    dayDate = output.format(input.parse(it.dayDate))
                )
            } else {
                return@map it.copy(
                    isSelected = false,
                    dayDate = output.format(input.parse(it.dayDate))
                )
            }

        }.let {
            _dates.value = it
        }

    }

    fun setDatesFromAPI(eventsAndAttractions: List<DTFilter>) {

        val input = SimpleDateFormat("yyyy-MM-dd")
        val output = SimpleDateFormat("dd MMMM,yyyy")

        var dateList: MutableList<Duration> = mutableListOf()

        eventsAndAttractions.mapIndexed { index, eventsAndAttraction ->
            if (index == 0) {
                dateList.add(
                    Duration(
                        index,
                        dayDate = output.format(input.parse(eventsAndAttraction.date)),
                        hour = eventsAndAttraction.hours + " hours",
                        isDay = if (eventsAndAttraction.type == "Day") 1 else 2,
                        isSelected = true
                    )
                )
            } else {

                dateList.add(
                    Duration(
                        index,
                        dayDate = output.format(input.parse(eventsAndAttraction.date)),
                        hour = eventsAndAttraction.hours + " hours",
                        isDay = if (eventsAndAttraction.type == "Day") 1 else 2,
                        isSelected = false
                    )
                )

            }
        }
        _dates.value = dateList

    }

    fun mapDistanceInList(distanceMatrixResponse: DistanceMatrixResponse, travelMode: String) {
        val data = _eventAttractionList.value ?: return
        data.mapIndexed { index, eventsAndAttraction ->
            return@mapIndexed eventsAndAttraction.copy(
                duration = distanceMatrixResponse.rows[0].elements[index].duration.text,
                distance = distanceMatrixResponse.rows[0].elements[index].distance.text,
                travelMode = travelMode
            )
        }.let {
            _tripList.value = it
        }

    }

    fun validateStep3(): Boolean {

        val data = _nearestLocationType.value ?: return false

        for (cat in data) {
            if (cat.isChecked) {
                return true
            }
        }

        return false

    }


}