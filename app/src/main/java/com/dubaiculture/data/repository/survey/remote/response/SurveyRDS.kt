package com.dubaiculture.data.repository.survey.remote.response

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.survey.mapper.transformFormIntoDTO
import com.dubaiculture.data.repository.survey.request.Form
import com.dubaiculture.data.repository.survey.service.SurveyService
import javax.inject.Inject

class SurveyRDS @Inject constructor(private val surveyService: SurveyService) :
    BaseRDS(surveyService) {
    suspend fun getSurveyForm(formId: String, locale: String): Result<SurveyFormResponse> =
        safeApiCall {
            surveyService.getSurveyForm(formId,locale)
        }

    suspend fun postSurvey(form : Form): Result<SurveyFormResponse> =
        safeApiCall {
            surveyService.postSurvey(transformFormIntoDTO(form))
        }
}