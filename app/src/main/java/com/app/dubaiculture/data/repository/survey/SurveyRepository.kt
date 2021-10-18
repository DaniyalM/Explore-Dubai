package com.app.dubaiculture.data.repository.survey

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.survey.mapper.transformSurvey
import com.app.dubaiculture.data.repository.survey.remote.response.SurveyFormResponse
import com.app.dubaiculture.data.repository.survey.request.Form
import com.app.dubaiculture.data.repository.survey.remote.response.SurveyRDS
import javax.inject.Inject

class SurveyRepository @Inject constructor(
    private val surveyRDS: SurveyRDS
) : BaseRepository(surveyRDS) {

    suspend fun getSurvey(formId : String , locale : String): Result<Form> {
        return when (val resultRDS = surveyRDS.getSurveyForm(formId,locale)) {
            is Result.Success -> {
                if (resultRDS.value.succeeded) {
                    Result.Success(transformSurvey(resultRDS.value))
                } else {
                    Result.Failure(false, null, null, resultRDS.value.errorMessage)
                }
            }
            is Result.Failure -> resultRDS
            is Result.Error -> resultRDS
        }
    }



    suspend fun postSurvey(form: Form): Result<SurveyFormResponse> {
        return when (val resultRDS = surveyRDS.postSurvey(form)) {
            is Result.Success -> {
                if (resultRDS.value.succeeded) {
                    Result.Success(resultRDS.value)
                } else {
                    Result.Failure(false, null, null, resultRDS.value.errorMessage)
                }
            }
            is Result.Failure -> resultRDS
            is Result.Error -> resultRDS
        }
    }

}