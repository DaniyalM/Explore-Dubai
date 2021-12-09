package com.dubaiculture.ui.postLogin.search.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchSharedViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {

     var _isAtoZ: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isAtoZ: LiveData<Event<Boolean>> = _isAtoZ
     var _isZtoA: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isZtoA: LiveData<Event<Boolean>> = _isZtoA
     var _isOld: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isOld: LiveData<Event<Boolean>> = _isOld
     var _isNew: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isNew: LiveData<Event<Boolean>> = _isNew

     var _isAtoZDone: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isAtoZDone: LiveData<Event<Boolean>> = _isAtoZDone
     var _isZtoADone: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isZtoADone: LiveData<Event<Boolean>> = _isZtoADone
     var _isOldDone: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isOldDone: LiveData<Event<Boolean>> = _isOldDone
     var _isNewDone: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isNewDone: LiveData<Event<Boolean>> = _isNewDone



    fun updateAscendSort() {
        _isAtoZ.value = Event(!_isAtoZ.value!!.peekContent())
        if (_isZtoA.value!!.peekContent()) {
            _isZtoA.value = Event(false)
        }

    }

    fun updateDescendSort() {
        _isZtoA.value = Event(!_isZtoA.value!!.peekContent())
        if (_isAtoZ.value!!.peekContent()) {
            _isAtoZ.value = Event(false)
        }

    }

    fun updateOldData() {
        _isOld.value = Event(!_isOld.value!!.peekContent())
        if (_isNew.value!!.peekContent()) {
            _isNew.value = Event(false)
        }
    }

    fun updateNewData() {
        _isNew.value = Event(!_isNew.value!!.peekContent())
        if (_isOld.value!!.peekContent()) {
            _isOld.value = Event(false)
        }
    }


    fun onDoneClicked() {
        _isAtoZDone.value = Event(!_isAtoZ.value!!.peekContent())
        if (_isZtoADone.value!!.peekContent()) {
            _isZtoADone.value = Event(false)
        }
        _isZtoADone.value = Event(!_isZtoA.value!!.peekContent())
        if (_isAtoZDone.value!!.peekContent()) {
            _isAtoZDone.value = Event(false)
        }
        _isOldDone.value = Event(!_isOld.value!!.peekContent())
        if (_isNewDone.value!!.peekContent()) {
            _isNewDone.value = Event(false)
        }
        _isNewDone.value = Event(!_isNew.value!!.peekContent())
        if (_isOldDone.value!!.peekContent()) {
            _isOldDone.value = Event(false)
        }
    }


}