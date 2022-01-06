package com.dubaiculture.ui.postLogin.plantrip.viewmodels

import com.dubaiculture.data.repository.trip.TripRepository
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.trip.local.DistanceDirectionListModel
import com.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.dubaiculture.data.repository.trip.remote.response.DirectionResponse
import com.dubaiculture.data.repository.trip.remote.response.DistanceMatrixResponse
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class MyTripViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val tripRepository: TripRepository
) : BaseViewModel(application) {

    val _directionResponse: MutableLiveData<MutableList<DirectionResponse>> = MutableLiveData()
    val directionResponse: LiveData<MutableList<DirectionResponse>> = _directionResponse
    val directionResponseList: MutableList<DirectionResponse> = mutableListOf()

    val _directionDistanceResponse: MutableLiveData<DistanceDirectionListModel> = MutableLiveData()
    val directionDistanceResponse: LiveData<DistanceDirectionListModel> = _directionDistanceResponse

    val _distanceResponse: MutableLiveData<MutableList<DistanceMatrixResponse>> = MutableLiveData()
    val distanceResponse: LiveData<MutableList<DistanceMatrixResponse>> = _distanceResponse
    val distanceResponseList: MutableList<DistanceMatrixResponse> = mutableListOf()

    private var _deleteTripStatus: MutableLiveData<Event<Boolean>> = MutableLiveData()
    var deleteTripStatus: MutableLiveData<Event<Boolean>> = _deleteTripStatus

    fun getDirections(
        list: List<EventsAndAttraction>,
        mapKey: String,
        travelMode: String,
        currentLanguage: Locale
    ) {
        viewModelScope.launch {
            showLoader(true)

            val responseBody = list.mapIndexed { index, it ->
                async {

                    if (index == list.size - 1)
                        return@async Result.Failure(false, 222)
                    var hashMap: HashMap<String, String> = HashMap<String, String>()

                    hashMap["origin"] =
                        it.latitude.toString() + "," + it.longitude.toString()

                    hashMap["language"] = currentLanguage.language

                    hashMap["destination"] =
                        list[index + 1].latitude.toString() + "," + list[index + 1].longitude.toString()
                    hashMap["mode"] = travelMode

                    hashMap["key"] = mapKey

                    val result = tripRepository.getDirections(hashMap)
                    return@async result

                }


            }.awaitAll()



            for (response1 in responseBody) {

                when (response1) {
                    is Result.Success -> {
                        directionResponseList.add(response1.value)

                    }
                    is Result.Error -> {
                        showLoader(false)
                        showToast(Constants.Error.SOMETHING_WENT_WRONG)
//                     result.exception
                    }
                    is Result.Failure -> {
                        if (response1.errorCode != 222) {
                            showLoader(false)
                            showToast(Constants.Error.SOMETHING_WENT_WRONG)
                        }
                    }
                }
            }

            showLoader(false)
            _directionResponse.value = directionResponseList
            getDistance(list, mapKey, travelMode, currentLanguage)
        }

//            val result = tripRepository.getDirections(map)
//            when (result) {
//                is Result.Success -> {
//                    showLoader(false)
//                    _directionResponse.value = result.value
//                }
//                is Result.Error -> {
//                    showLoader(false)
//                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
////                     result.exception
//                }
//                is Result.Failure -> {
//                    showLoader(false)
//                    showToast(Constants.Error.SOMETHING_WENT_WRONG)
//
////                    _userType.value = result
//                }
//            }
    }


    fun getDistance(
        list: List<EventsAndAttraction>,
        mapKey: String,
        travelMode: String,
        currentLanguage: Locale
    ) {

        viewModelScope.launch {
            showLoader(true)

            val responseBody = list.mapIndexed { index, it ->
                async {

                    if (index == list.size - 1)
                        return@async Result.Failure(false, 222)
                    var hashMap: HashMap<String, String> = HashMap<String, String>()

                    hashMap["origins"] =
                        it.latitude.toString() + "," + it.longitude.toString()

                    hashMap["language"] = currentLanguage.language

                    hashMap["destinations"] =
                        list[index + 1].latitude.toString() + "," + list[index + 1].longitude.toString()
                    hashMap["mode"] = travelMode

                    hashMap["key"] = mapKey

                    val result = tripRepository.getDistance(hashMap)
                    return@async result

                }


            }.awaitAll()



            for (response1 in responseBody) {

                when (response1) {
                    is Result.Success -> {
                        distanceResponseList.add(response1.value)

                    }
                    is Result.Error -> {
                        showLoader(false)
                        showToast(Constants.Error.SOMETHING_WENT_WRONG)
//                     result.exception
                    }
                    is Result.Failure -> {
                        if (response1.errorCode != 222) {
                            showLoader(false)
                            showToast(Constants.Error.SOMETHING_WENT_WRONG)
                        }
                    }
                }
            }

            showLoader(false)
            _distanceResponse.value = distanceResponseList
            _directionDistanceResponse.value =
                DistanceDirectionListModel(directionResponseList, distanceResponseList)
        }

    }

    fun deleteTrip(tripId: String) {
        viewModelScope.launch {
            showLoader(true)
            val result = tripRepository.deleteTrip(tripId)
            when (result) {
                is Result.Success -> {
                    showLoader(false)
//                    showErrorDialog(
//                        message = result.value.saveTripResponseDTO.message,
//                        colorBg = R.color.purple_900
//                    )
                    _deleteTripStatus.value = Event(true)
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


}