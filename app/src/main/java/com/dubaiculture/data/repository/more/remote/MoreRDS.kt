package com.dubaiculture.data.repository.more.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequestDTO
import com.dubaiculture.data.repository.more.remote.request.ShareFeedBackRequestDTO
import com.dubaiculture.data.repository.more.remote.response.notification.NotificationDTO
import com.dubaiculture.data.repository.more.remote.response.notification.NotificationRequestDTO
import com.dubaiculture.data.repository.more.service.MoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoreRDS @Inject constructor(private val moreService: MoreService) : BaseRDS(moreService) {
    suspend fun getPrivacyPolicy(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO) =
        safeApiCall {
            moreService.getPrivacyPolicy(privacyAndTermRequestDTO.culture)
        }

    suspend fun getTermsAndConditions(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO) =
        safeApiCall {
            moreService.getTermsAndCondition(privacyAndTermRequestDTO.culture)
        }

    suspend fun getContactCenter(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO) =
        safeApiCall {
            moreService.getContactCenter(privacyAndTermRequestDTO.culture)
        }

    suspend fun getFaqs(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO) =
        safeApiCall {
            moreService.getFAQs(privacyAndTermRequestDTO.culture)
        }

    suspend fun getCultureConnoisseur(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO) =
        safeApiCall {
            moreService.getCultureConnoisseur(privacyAndTermRequestDTO.culture)
        }

    suspend fun getFeedBackType(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO) = safeApiCall {
        moreService.getFeedBackType(privacyAndTermRequestDTO.culture)
    }

    suspend fun postFeedBack(shareFeedBackRequestDTO: ShareFeedBackRequestDTO) =
        safeApiCall {
            moreService.postFeedBack(shareFeedBackRequestDTO)
        }

    suspend fun getNotificationCount(culture: String) =
        safeApiCall {
            moreService.getMyNotificationCount(culture)
        }

    suspend fun getPaginatedNotification(notificationRequestDTO: NotificationRequestDTO): Result<Flow<PagingData<NotificationDTO>>> {
        return safeApiCall {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    NotificationPagingSource(
                        moreService,
                        notificationRequestDTO
                    )
                }
            ).flow
        }

    }

}