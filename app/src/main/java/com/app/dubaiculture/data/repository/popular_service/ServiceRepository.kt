package com.app.dubaiculture.data.repository.popular_service

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.popular_service.local.models.EServices
import com.app.dubaiculture.data.repository.popular_service.mapper.transformService
import com.app.dubaiculture.data.repository.popular_service.remote.ServiceRDS
import com.app.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val serviceRDS: ServiceRDS
) : BaseRepository(serviceRDS) {
    suspend fun getEServices(eServiceRequest: EServiceRequest): Result<EServices> {
        return when (val resultRDS = serviceRDS.getEServices(eServiceRequest)) {
            is Result.Success -> {
                if (resultRDS.value.succeeded) {
                    Result.Success(transformService(resultRDS.value))
                } else {
                    Result.Failure(false, null, null, resultRDS.value.errorMessage)
                }
            }
            is Result.Failure -> resultRDS
            is Result.Error -> resultRDS
        }
    }


}