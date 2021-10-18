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
       suspend fun getEServicesDetail(eServiceRequest: EServiceRequest)=safeApiCall {
            popularService.getEServicesDetail(eServiceRequest.culture, eServiceRequest.id.toString())
        }

    suspend fun getDocument(url:String?)
    =safeApiCall {
        popularService.getDocument(url?:"http://www.africau.edu/images/default/sample.pdf")
    }
}