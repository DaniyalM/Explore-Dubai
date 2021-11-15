package com.dubaiculture.data.repository.eservices.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetTokenResponse
import com.dubaiculture.data.repository.eservices.service.EService
import com.dubaiculture.data.repository.trip.remote.response.UserTypeResponse
import com.dubaiculture.data.repository.trip.service.MapService
import com.dubaiculture.data.repository.trip.service.TripService
import javax.inject.Inject

class EServicesRDS @Inject constructor(
    private val eService: EService
) : BaseRDS() {
    suspend fun getEServiceToken(getTokenRequestParam: GetTokenRequestParam): Result<GetTokenResponse> =
        safeApiCall {
            eService.getEServiceToken(getTokenRequestParam)
        }

    suspend fun getFieldValue(getFieldValueRequestDTO: GetFieldValueRequestDTO): Result<GetFieldValueResponse> =
        safeApiCall {
            eService.getFieldValue(getFieldValueRequestDTO)
        }

}