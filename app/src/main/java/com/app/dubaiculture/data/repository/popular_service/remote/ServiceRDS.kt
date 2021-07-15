package com.app.dubaiculture.data.repository.popular_service.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.app.dubaiculture.data.repository.popular_service.service.PopularService
import javax.inject.Inject

class ServiceRDS @Inject constructor(private val popularService: PopularService) :
    BaseRDS(popularService) {
        suspend fun getEServices(eServiceRequest: EServiceRequest)=safeApiCall {
            popularService.getEServices(eServiceRequest.culture)
        }
}