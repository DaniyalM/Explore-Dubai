package com.dubaiculture.data.repository.popular_service.remote

import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.popular_service.mapper.transformServiceRequest
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.dubaiculture.data.repository.popular_service.service.PopularService
import javax.inject.Inject

class ServiceRDS @Inject constructor(private val popularService: PopularService) :
    BaseRDS(popularService) {
    suspend fun getEServices(eServiceRequest: EServiceRequest) = safeApiCall {
        popularService.getEServices(eServiceRequest.culture!!)
    }

    suspend fun getEServicesDetail(eServiceRequest: EServiceRequest) = safeApiCall {
        popularService.getEServicesDetail(eServiceRequest.culture!!, eServiceRequest.id.toString())
    }

    suspend fun postServiceComment(eServiceRequest: EServiceRequest) = safeApiCall {
        popularService.postCommentService(transformServiceRequest(eServiceRequest))
    }

    suspend fun getDocument(url: String?) = safeApiCall {
        popularService.getDocument(url ?: "http://www.africau.edu/images/default/sample.pdf")
    }
}