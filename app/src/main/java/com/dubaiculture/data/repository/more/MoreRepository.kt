package com.dubaiculture.data.repository.more

import androidx.paging.PagingData
import androidx.paging.map
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.more.local.ContactCenter
import com.dubaiculture.data.repository.more.mapper.*
import com.dubaiculture.data.repository.more.remote.MoreRDS
import com.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.dubaiculture.data.repository.more.remote.request.ShareFeedbackRequest
import com.dubaiculture.data.repository.more.remote.response.notification.NotificationRequest
import com.dubaiculture.data.repository.more.remote.response.notification.Notifications
import com.dubaiculture.data.repository.popular_service.local.models.EServices
import com.dubaiculture.data.repository.popular_service.mapper.transformService
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    suspend fun getFaqs(privacyAndTermRequest: PrivacyAndTermRequest) =
        when (val result =
            moreRDS.getFaqs(transformPrivacyAndTermsRequest(privacyAndTermRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(
                        transformFAQsRequest(result.value)
                    )
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }

    suspend fun getCultureConnoisseur(privacyAndTermRequest: PrivacyAndTermRequest) =
        when (val result =
            moreRDS.getCultureConnoisseur(transformPrivacyAndTermsRequest(privacyAndTermRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(
                        Event(
                            transformCultureConnoisseur(result.value)
                        )
                    )
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }

    suspend fun getFeedbackType(privacyAndTermRequest: PrivacyAndTermRequest) =
        when (val result =
            moreRDS.getFeedBackType(transformPrivacyAndTermsRequest(privacyAndTermRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                    Result.Success(
                        Event(
                            transformFeedbackType(result.value)
                        )
                    )
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }


    suspend fun postFeedBack(shareFeedbackRequest: ShareFeedbackRequest) =
        when (val result =
            moreRDS.postFeedBack(transformPostFeedBack(shareFeedbackRequest))) {
            is Result.Success -> {
                if (result.value.succeeded) {
                   Result.Success(
                       transformPostFeedBackMessage(result.value)
//                            Event((result.value))

                    )
                } else {
                    Result.Failure(false, null, null, result.value.errorMessage)
                }
            }
            is Result.Error -> result
            is Result.Failure -> result
        }


    suspend fun getNotification(
        notificationRequest: NotificationRequest,
        callback: (count: Int) -> Unit,
    ): Result<Flow<PagingData<Notifications>>> {
        val result = moreRDS.getPaginatedNotification(transformNotification(notificationRequest),callback)
        return if (result is Result.Success) {
            Result.Success(result.value.map {
                it.map {
                    transformNotificationPaging(it)
                }
            })

        } else {
            Result.Failure(true, null, null, Constants.Error.SOMETHING_WENT_WRONG)
        }
    }

    suspend fun getNotificationCount(culture: String): Result<String> {
        val result = moreRDS.getNotificationCount(culture)
        return if (result is Result.Success) {
            Result.Success(result.value.Result.Count ?: "0")
        } else {
            Result.Failure(true, null, null, Constants.Error.SOMETHING_WENT_WRONG)
        }
    }

    suspend fun getEServices(eServiceRequest: EServiceRequest): Result<EServices> {
        return when (val resultRDS = moreRDS.getEServices(eServiceRequest)) {
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