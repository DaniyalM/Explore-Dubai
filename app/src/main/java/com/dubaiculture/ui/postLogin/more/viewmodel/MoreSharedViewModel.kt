package com.dubaiculture.ui.postLogin.more.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreSharedViewModel @Inject constructor(
    application: Application,
) :BaseViewModel(application){
    val _isLogged: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isLogged: LiveData<Event<Boolean>> = _isLogged

}