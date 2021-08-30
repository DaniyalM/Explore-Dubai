package com.app.dubaiculture.ui.postLogin.survey.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.survey.SurveyRepository
import com.app.dubaiculture.data.repository.survey.request.Form
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SurveyViewModel @ViewModelInject constructor(
    application: Application,
    private val surveyRepository: SurveyRepository
) : BaseViewModel(application) {

    private val _form: MutableLiveData<Event<Form>> = MutableLiveData()
    val form: LiveData<Event<Form>> = _form

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




    fun postSurvey(form: Form) {
        viewModelScope.launch {
            showLoader(true)
            val result = surveyRepository.postSurvey(form = form)

            if (result is Result.Success) {
                showLoader(false)

            } else if (result is Result.Failure) {
                showLoader(false)

            }
        }
    }
}