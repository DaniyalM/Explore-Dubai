package com.dubaiculture.data.repository.more.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.more.remote.request.ShareFeedBackRequestDTO
import com.dubaiculture.data.repository.more.remote.response.MoreResponse
import com.dubaiculture.data.repository.popular_service.remote.response.ServiceResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("Home/GetSharedFeedbackType")
    suspend fun getFeedBackType(@Query("culture") culture: String): MoreResponse

    @POST("Home/ShareFeedback")
    suspend fun postFeedBack(@Body shareFeedBackRequestDTO: ShareFeedBackRequestDTO): MoreResponse

    @GET("Content/GetEServices")
    suspend fun getEServices(@Query("culture") culture: String): ServiceResponse

    @GET("Content/GetMyNotifications")
    suspend fun getMyNotification(
        @Query("culture") culture: String ,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): MoreResponse

    @GET("Content/GetNotificationsCount")
    suspend fun getMyNotificationCount(
        @Query("culture") culture: String
    ): MoreResponse
}