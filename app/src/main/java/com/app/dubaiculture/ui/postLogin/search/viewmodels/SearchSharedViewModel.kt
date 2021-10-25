package com.app.dubaiculture.ui.postLogin.search.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchSharedViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {

    private var _isAtoZ: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isAtoZ: LiveData<Event<Boolean>> = _isAtoZ
    private var _isZtoA: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isZtoA: LiveData<Event<Boolean>> = _isZtoA
    private var _isOld: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isOld: LiveData<Event<Boolean>> = _isOld
    private var _isNew: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isNew: LiveData<Event<Boolean>> = _isNew

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


}