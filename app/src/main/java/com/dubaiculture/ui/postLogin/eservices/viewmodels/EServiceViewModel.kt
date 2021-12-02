package com.dubaiculture.ui.postLogin.eservices.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.eservices.EServicesRepository
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants.NavBundles.FORM_NAME
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EServiceViewModel @Inject constructor(
    application: Application,
    val eServicesRepository: EServicesRepository,
    val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(application) {

    private val _fieldValues: MutableLiveData<List<GetFieldValueItem>> = MutableLiveData()
    val fieldValues: LiveData<List<GetFieldValueItem>> = _fieldValues

    private val _fieldValue: MutableLiveData<Event<GetFieldValueItem>> = MutableLiveData()
    val fieldValue: LiveData<Event<GetFieldValueItem>> = _fieldValue

    init {
        savedStateHandle.get<String>(FORM_NAME)?.let {
            getFieldValues(it)
        }
    }


    private fun getFieldValues(formName: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                eServicesRepository.getFieldValue(GetFieldValueRequest(formName = formName))) {
                is Result.Success -> {
                    showLoader(false)
                    _fieldValues.value = result.value
                }
                is Result.Failure -> {
                    showLoader(false)
                }
            }
        }
    }


    fun updateFieldValue(field: GetFieldValueItem) {
        _fieldValue.value = Event(field)
    }

    fun updateList(field: GetFieldValueItem) {
        val data = _fieldValues.value ?: return
        data.map {
            if (field.index == it.index&& field.id==it.id)
                return@map field
            else
                return@map it
        }.let {
            _fieldValues.value = it
        }
    }


}