package com.dubaiculture.ui.postLogin.events.myevents.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.event.EventRepository
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyEventViewModel @Inject constructor(
    application: Application,
    private val eventRepository: EventRepository
) : BaseViewModel(application,eventRepository) {

    private val _myEvents: MutableLiveData<List<Events>> = MutableLiveData()
    val myEvents: LiveData<List<Events>> = _myEvents




    fun getMyEvent(locale : String){
        viewModelScope.launch {
            showLoader(true)
            val result = eventRepository.fetchMyEvent(locale)
                if(result is Result.Success){
                    showLoader(false)
                    _myEvents.value = result.value

                }else if(result is Result.Failure){
                    showLoader(false)
                }
            }
        }
    }