package com.app.dubaiculture.data.repository.more.mapper

import com.app.dubaiculture.data.repository.more.local.PrivacyPolicy
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequestDTO
import com.app.dubaiculture.data.repository.more.remote.response.MoreResponse

fun transformPrivacyAndTermsRequest(privacyAndTermRequest: PrivacyAndTermRequest)=PrivacyAndTermRequestDTO(
        culture = privacyAndTermRequest.culture
)
fun transformPrivacyResponse(moreresponse: MoreResponse) =
    moreresponse.Result.privacyPolicy[0].run {
        PrivacyPolicy(
            title = Title!!,
            description = Description!!,
        )
    }
fun transformTermsAndConditionsResponse(moreresponse: MoreResponse) =
    moreresponse.Result.termsAndCondition[0].run {
        PrivacyPolicy(
            title = Title!!,
            description = Description!!,
        )
    }