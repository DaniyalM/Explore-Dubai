package com.dubaiculture.ui.postLogin.attractions.detail.ibecon.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.visited.VisitedRepository
import com.dubaiculture.data.repository.visited.remote.request.AddVisitedPlaceRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import com.dubaiculture.utils.toString
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
//    private val context = getApplication<ApplicationEntry>()

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
            showLoader(true)
            when (val result = visitedRepository.addVisitedPlace(addVisitedPlaceRequest)) {
                is Result.Success -> {
                    if (result.value == "Added") {
                        showLoader(false)
                        _isVisited.value = Event(true)
                    }
                }
                is Result.Failure -> {
                    showLoader(false)
                }

            }
        }


    }
}