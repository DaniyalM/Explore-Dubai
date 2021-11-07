package com.dubaiculture.data.repository.survey.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.more.local.GetMessage
import com.dubaiculture.data.repository.more.remote.response.MoreResponse
import com.dubaiculture.data.repository.survey.remote.response.SurveyFormResponse
import com.dubaiculture.data.repository.survey.request.FormDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SurveyService : BaseService {
    @GET("Content/GetSurveyForm")
    suspend fun getSurveyForm(
        @Query("id") id: String,
        @Query("culture") culture: String
    ): SurveyFormResponse

    @POST("Content/PostSurveyForm")
    suspend fun postSurvey(@Body formDTO: FormDTO): SurveyFormResponse



}