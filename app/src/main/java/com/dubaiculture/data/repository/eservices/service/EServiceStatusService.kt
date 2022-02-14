package com.dubaiculture.data.repository.eservices.service

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.eservices.remote.request.SubmitServiceTokenRequest
import com.dubaiculture.data.repository.eservices.remote.response.GetEServiceStatusResponse
import retrofit2.http.*

interface EServiceStatusService : BaseService {

    @POST("Content/AddUserEServicesRequest")
    suspend fun submitServiceToken(
        @Body eServicesHitModel: SubmitServiceTokenRequest
    ): BaseResponse

    @GET("Content/GetMyEServices")
    suspend fun getServiceStatusList(
        @Query("culture") culture: String
    ): GetEServiceStatusResponse

}