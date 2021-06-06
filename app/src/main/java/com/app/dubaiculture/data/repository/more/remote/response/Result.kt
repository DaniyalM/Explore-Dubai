package com.app.dubaiculture.data.repository.more.remote.response

import com.app.dubaiculture.data.repository.attraction.remote.response.SocialLinkDTO
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyPolicyDTO
import com.app.dubaiculture.data.repository.more.remote.request.TermsAndConditionDTO

data class Result(
    var PrivacyPolicy: List<PrivacyPolicyDTO>,
    var TermsAndCondition: List<TermsAndConditionDTO>,
    var Title: String,
    var Description: String,
    var ContactCenterLocation: ContactCenterLocationDTO,
    var ContactCenterReach: ContactCenterReachDTO,
    var ContactCenterFeedback: ContactCenterFeedbackDTO,
    var ContactCenterSuggestionComplains: ContactCenterSuggestionComplainsDTO,
    var SocialLinks: List<SocialLinkDTO>
)