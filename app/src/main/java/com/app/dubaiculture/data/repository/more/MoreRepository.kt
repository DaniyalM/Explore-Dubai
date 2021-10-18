package com.app.dubaiculture.data.repository.more

import androidx.paging.PagingData
import androidx.paging.map
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.more.local.ContactCenter
import com.app.dubaiculture.data.repository.more.mapper.*
import com.app.dubaiculture.data.repository.more.remote.MoreRDS
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.data.repository.more.remote.request.ShareFeedbackRequest
import com.app.dubaiculture.data.repository.more.remote.response.notification.NotificationRequest
import com.app.dubaiculture.data.repository.more.remote.response.notification.Notifications
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.mapper.transformNewsPaging
import com.app.dubaiculture.data.repository.news.mapper.transformNewsRequest
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
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


    suspend fun getNotification(notificationRequest: NotificationRequest): Result<Flow<PagingData<Notifications>>> {
        val result = moreRDS.getPaginatedNotification(transformNotification(notificationRequest))
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


}