package com.dubaiculture.ui.postLogin.survey.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.survey.SurveyRepository
import com.dubaiculture.data.repository.survey.request.Form
import com.dubaiculture.data.repository.survey.request.Items
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    application: Application,
    private val surveyRepository: SurveyRepository
) : BaseViewModel(application) {

    private val _form: MutableLiveData<Event<Form>> = MutableLiveData()
    val form: LiveData<Event<Form>> = _form

    private val _formSubmit: MutableLiveData<Event<Form>> = MutableLiveData()
    val formSubmit: LiveData<Event<Form>> = _formSubmit


    fun getSurveyForm(formId: String, locale: String) {
        viewModelScope.launch {
            showLoader(true)
            val result = surveyRepository.getSurvey(formId, locale)

            if (result is Result.Success) {
                showLoader(false)
                _form.value = Event(result.value)
            } else if (result is Result.Failure) {
                showLoader(false)

            }
        }
    }

     fun updateForm(form: Form) {
         _formSubmit.value = Event(form)
    }

     fun updateFormItem(form: Form,items: Items) {
        val data = form.items ?: return
        data.map {
            if (items.id == it.id)
                return@map items
            else return@map it
        }.let {
            updateForm(form.copy(items = it))
        }
    }


    fun postSurvey(form: Form,callback:()->Unit) {
        viewModelScope.launch {
            showLoader(true)
            val result = surveyRepository.postSurvey(form = form)
            if (result is Result.Success) {
                showLoader(false)
                callback()

            } else if (result is Result.Failure) {
                showToast(result.errorMessage.toString())
                showLoader(false)

            }
        }
    }
}