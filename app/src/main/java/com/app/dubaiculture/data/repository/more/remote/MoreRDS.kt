package com.app.dubaiculture.data.repository.more.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequestDTO
import com.app.dubaiculture.data.repository.more.service.MoreService
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

    suspend fun getCultureConnoisseur(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO)
    = safeApiCall {
        moreService.getCultureConnoisseur(privacyAndTermRequestDTO.culture)
    }

    suspend fun getFeedBackType(privacyAndTermRequestDTO: PrivacyAndTermRequestDTO)
            = safeApiCall {
        moreService.getFeedBackType(privacyAndTermRequestDTO.culture)
    }
}