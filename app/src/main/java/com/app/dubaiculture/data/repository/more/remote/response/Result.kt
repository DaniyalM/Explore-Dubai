package com.app.dubaiculture.data.repository.more.remote.response

import com.app.dubaiculture.data.repository.more.local.PrivacyPolicy
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyPolicyDTO
import com.app.dubaiculture.data.repository.more.remote.request.TermsAndConditionDTO
import com.app.dubaiculture.data.repository.news.remote.request.LatestNewsDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("PrivacyPolicy")
    @Expose
    var privacyPolicy: List<PrivacyPolicyDTO>,
    @SerializedName("TermsAndCondition")
    @Expose
    var termsAndCondition: List<TermsAndConditionDTO>
)