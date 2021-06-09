package com.app.dubaiculture.data.repository.more.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.more.remote.response.MoreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoreService : BaseService {

    @GET("Content/GetPrivacyPolicy")
    suspend fun getPrivacyPolicy(@Query("culture") culture: String): MoreResponse

    @GET("Content/GetTermsAndCondition")
    suspend fun getTermsAndCondition(@Query("culture") culture: String): MoreResponse

    @GET("Content/GetContactCenter")
    suspend fun getContactCenter(@Query("culture") culture: String): MoreResponse

    @GET("Content/GetFAQ")
    suspend fun getFAQs(@Query("culture") culture: String): MoreResponse

    @GET("Content/GetCultureConnoisseur")
    suspend fun getCultureConnoisseur(@Query("culture") culture: String): MoreResponse
}