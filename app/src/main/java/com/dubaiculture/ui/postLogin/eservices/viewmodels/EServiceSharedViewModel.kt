package com.dubaiculture.ui.postLogin.eservices.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EServiceSharedViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {

    private var _updateField: MutableLiveData<Event<GetFieldValueItem>> = MutableLiveData()
    var updateField: MutableLiveData<Event<GetFieldValueItem>> = _updateField


    fun updateField(getFieldValueItem: GetFieldValueItem) {
        _updateField.value = Event(getFieldValueItem)
    }
}