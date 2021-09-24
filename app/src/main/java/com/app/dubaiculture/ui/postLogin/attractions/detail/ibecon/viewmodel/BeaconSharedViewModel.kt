package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.visited.VisitedRepository
import com.app.dubaiculture.data.repository.visited.remote.request.AddVisitedPlaceRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.toString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BeaconSharedViewModel @Inject constructor(
    application: Application,
    private val visitedRepository: VisitedRepository,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()

    val _isVisited: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isVisited: LiveData<Event<Boolean>> = _isVisited

    suspend fun markAsVisited(itemId: String, deviceId: String) {
        val date: Date = Calendar.getInstance().time
        val format = "yyyy-MM-dd"
        val strTime = date.toString(format)

        val addVisitedPlaceRequest = AddVisitedPlaceRequest(
            itemId = itemId,
            deviceId = deviceId,
            type = 1,
            addedOn = strTime
        )


        viewModelScope.launch {
            when (val result = visitedRepository.addVisitedPlace(addVisitedPlaceRequest)) {
                is Result.Success -> {
                    if (result.value == "Added") {
                        _isVisited.value = Event(true)
                    }
                }
                is Result.Failure -> {
                }

            }
        }


    }
}