package com.dubaiculture.ui.postLogin.eservices.viewmodels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.AuthUtils.isEmailValid
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EServiceBottomSheetViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {


    var field: ObservableField<String> = ObservableField("")
    val btnSubmitObserver: ObservableBoolean = ObservableBoolean(false)
    val isEmail: ObservableBoolean = ObservableBoolean(false)

    var _fieldValue: MutableLiveData<Event<String>> = MutableLiveData()
    var fieldValue: LiveData<Event<String>> = _fieldValue

    fun onFieldChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        if (isEmail.get()) {
            btnSubmitObserver.set(
                isEmailValid(s.toString().trim())
            )
            if (!isEmailValid(s.toString().trim()))
                showToast("Enter valid input !")
        } else {
            btnSubmitObserver.set(
                s.toString().trim()
                    .isNotEmpty()
            )
        }

    }


    fun postField() {
        _fieldValue.value = Event(field.get().toString().trim())
    }


}