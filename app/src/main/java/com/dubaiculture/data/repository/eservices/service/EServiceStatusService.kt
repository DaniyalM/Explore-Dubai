package com.dubaiculture.data.repository.eservices.service

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.eservices.remote.request.SubmitServiceTokenRequest
import retrofit2.http.*

interface EServiceStatusService : BaseService {

    @POST("Content/AddEServicesHit")
    suspend fun submitServiceToken(
        @Body eServicesHitModel: SubmitServiceTokenRequest
    ): BaseResponse

}