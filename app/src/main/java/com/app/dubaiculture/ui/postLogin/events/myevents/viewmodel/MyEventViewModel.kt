package com.app.dubaiculture.ui.postLogin.events.myevents.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.EventRepository
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch

class MyEventViewModel @ViewModelInject constructor(
    application: Application,
    private val eventRepository: EventRepository
) : BaseViewModel(application) {

    private val _myEvents: MutableLiveData<List<Events>> = MutableLiveData()
    val myEvents: LiveData<List<Events>> = _myEvents

    var subject: ObservableField<String> = ObservableField("")
    var type: ObservableField<String> = ObservableField("")

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