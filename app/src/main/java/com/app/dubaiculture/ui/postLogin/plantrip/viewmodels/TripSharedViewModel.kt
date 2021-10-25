package com.app.dubaiculture.ui.postLogin.plantrip.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.trip.TripRepository
import com.app.dubaiculture.data.repository.trip.local.*
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripSharedViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    val _showPlan: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val showPlan: LiveData<Event<Boolean>> = _showPlan

    private val _durations: MutableLiveData<Durations> = MutableLiveData()
    val durations: LiveData<Durations> = _durations

    val _duration: MutableLiveData<List<Duration>> = MutableLiveData()
    val duration: LiveData<List<Duration>> = _duration

    val _type: MutableLiveData<LocationNearest> = MutableLiveData()
    val type: LiveData<LocationNearest> = _type


    val _usersType: MutableLiveData<List<LocationNearest>> = MutableLiveData()
    val usersType: LiveData<List<LocationNearest>> = _usersType

    private val _nearestLocation: MutableLiveData<NearestLocation> = MutableLiveData()
    val nearestLocation: LiveData<NearestLocation> = _nearestLocation

    init {
        getNearestLocation()
    }


    private fun getNearestLocation() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getNearestLocation()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _nearestLocation.value = result.value
                }
                is Result.Error -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
//                     result.exception
                }
                is Result.Failure -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)

//                    _userType.value = result
                }
            }
        }
    }

    fun updateUserItem(userType: LocationNearest) {
        _type.value = userType
    }

    fun updateInUserTypeList(userType: LocationNearest) {

        val data = _usersType.value ?: return
        val updateData = updateToDefault(data)
        updateData.map {
            if (userType.locationId == it.locationId
            ) return@map userType
            else {
                return@map it
            }
        }.let {

            _usersType.value = it
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

    fun getDurations() {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.getDurations()
            when (result) {
                is Result.Success -> {
                    showLoader(false)
                    _durations.value = result.value
                }
                is Result.Error -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
                }
                is Result.Failure -> {
                    showLoader(false)
                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
                }
            }
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
                isDay = 0
            )

        }

        _duration.value = durationList


    }

     fun getList(days: Int) {

        var daysList: MutableList<String> = mutableListOf<String>()

        for (i in 1 until days + 1) {
            daysList.add("${String.format("%02d", i)} Day")
        }

        populateList(daysList)

    }

    fun repeatToAll(repeatToAll:Boolean,duration: Duration){

        if(repeatToAll){

            val daysList: List<Duration> = _duration.value?:return

            daysList.map {

                return@map it.copy(
                    dayDate = it.dayDate,
                    hour = duration.hour,
                    isDay = duration.isDay,
                    id = it.id
                )
            }.let {
                _duration.value = it
            }


        }

    }

}