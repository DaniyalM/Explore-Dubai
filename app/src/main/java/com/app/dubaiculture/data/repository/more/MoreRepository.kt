package com.app.dubaiculture.data.repository.more

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.more.local.ContactCenter
import com.app.dubaiculture.data.repository.more.mapper.transformPrivacyAndTermsRequest
import com.app.dubaiculture.data.repository.more.mapper.transformPrivacyResponse
import com.app.dubaiculture.data.repository.more.mapper.transformTermsAndConditionsResponse
import com.app.dubaiculture.data.repository.more.mapper.transformationContactCenter
import com.app.dubaiculture.data.repository.more.remote.MoreRDS
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.utils.event.Event
import javax.inject.Inject

class MoreRepository @Inject constructor(private val moreRDS: MoreRDS) : BaseRepository(moreRDS) {

    suspend fun getPrivacyPolicy(privacyAndTermRequest: PrivacyAndTermRequest) =
        when (val result =
            moreRDS.getPrivacyPolicy(transformPrivacyAndTermsRequest(privacyAndTermRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(Event(transformPrivacyResponse(result.value)))
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }

    suspend fun getTermsAndCondition(privacyAndTermRequest: PrivacyAndTermRequest) =
        when (val result =
            moreRDS.getTermsAndConditions(transformPrivacyAndTermsRequest(privacyAndTermRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(Event(transformTermsAndConditionsResponse(result.value)))
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }


    suspend fun getContactCenter(privacyAndTermRequest: PrivacyAndTermRequest): Result<Event<ContactCenter>> =
        when (val result =
            moreRDS.getContactCenter(transformPrivacyAndTermsRequest(privacyAndTermRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(
                        Event(
                            transformationContactCenter(result.value)
                        )
                    )
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Failure -> result
            is Result.Error -> result
        }

}