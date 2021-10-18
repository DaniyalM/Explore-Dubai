package com.app.dubaiculture.data.repository.popular_service.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.popular_service.remote.response.ServiceResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PopularService : BaseService {

    @Streaming
    @GET
    suspend fun getDocument(@Url fileUrl: String): Response<ResponseBody>

    @GET("Content/GetEServices")
    suspend fun getEServices(@Query("culture") culture: String): ServiceResponse

    @GET("Content/GetEServicesDetail")
    suspend fun getEServicesDetail(
        @Query("culture") culture: String,
        @Query("Id") Id: String
    ): ServiceResponse


}