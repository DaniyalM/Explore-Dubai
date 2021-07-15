package com.app.dubaiculture.data.repository.popular_service.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.popular_service.remote.response.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularService : BaseService {


    @GET("Content/GetEServices")
    suspend fun getEServices(@Query("culture") culture: String): ServiceResponse
}