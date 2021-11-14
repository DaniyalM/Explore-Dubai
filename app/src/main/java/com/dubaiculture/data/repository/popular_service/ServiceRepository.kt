package com.dubaiculture.data.repository.popular_service

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.popular_service.local.models.EServices
import com.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.dubaiculture.data.repository.popular_service.mapper.transformService
import com.dubaiculture.data.repository.popular_service.mapper.transformServiceDetail
import com.dubaiculture.data.repository.popular_service.remote.ServiceRDS
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.dubaiculture.utils.Constants.Error.SOMETHING_WENT_WRONG
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val serviceRDS: ServiceRDS
) : BaseRepository(serviceRDS) {

    suspend fun postUpvote(eServiceRequest: EServiceRequest): Result<Boolean> {
        return when (val resultRDS = serviceRDS.postUpvote(eServiceRequest)) {
            is Result.Success -> {
                if (resultRDS.value.succeeded) {
                    Result.Success(true)
                } else {
                    Result.Failure(false, null, null, resultRDS.value.errorMessage)
                }
            }
            is Result.Failure -> resultRDS
            is Result.Error -> resultRDS
        }
    }
    suspend fun postServiceComment(eServiceRequest: EServiceRequest): Result<Boolean> {
        return when (val resultRDS = serviceRDS.postServiceComment(eServiceRequest)) {
            is Result.Success -> {
                if (resultRDS.value.succeeded) {
                    Result.Success(true)
                } else {
                    Result.Failure(false, null, null, resultRDS.value.errorMessage)
                }
            }
            is Result.Failure -> resultRDS
            is Result.Error -> resultRDS
        }
    }

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

    suspend fun getEServiceDetail(eServiceRequest: EServiceRequest): Result<EServicesDetail> {
        return when (val resultRds = serviceRDS.getEServicesDetail(eServiceRequest)) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(transformServiceDetail(resultRds.value.Result.EServicesDetail))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

    suspend fun getDoc(url:String)=
        when(val result=serviceRDS.getDocument(url)){
            is Result.Success ->{
                if (result.value.isSuccessful) {
                    Result.Success(result.value.body())
                } else {
                    Result.Failure(false, null, null, SOMETHING_WENT_WRONG)
                }
            }
            else -> Result.Failure(false, null, null, SOMETHING_WENT_WRONG)

        }


}