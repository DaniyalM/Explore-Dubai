package com.app.dubaiculture.data.repository.more.remote.response

import com.app.dubaiculture.data.repository.attraction.remote.response.SocialLinkDTO
import com.app.dubaiculture.data.repository.more.remote.request.FaqItemDTO
import com.app.dubaiculture.data.repository.more.remote.request.FeedbacksTypeDTO
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyPolicyDTO
import com.app.dubaiculture.data.repository.more.remote.request.TermsAndConditionDTO
import com.app.dubaiculture.data.repository.more.remote.response.notification.NotificationDTO

data class Result(
    var PrivacyPolicy: List<PrivacyPolicyDTO>,
    var TermsAndCondition: List<TermsAndConditionDTO>,
    var MessageBody: String,
    var Title: String,
    var Description: String,
    val DubaiLogo: String,
    val GovtLogo: String,
    val ReadMoreTitle: String,
    val ReadMoreURL: String,
    var ContactCenterLocation: ContactCenterLocationDTO,
    var ContactCenterReach: ContactCenterReachDTO,
    var ContactCenterFeedback: ContactCenterFeedbackDTO,
    var ContactCenterSuggestionComplains: ContactCenterSuggestionComplainsDTO,
    var SocialLinks: List<SocialLinkDTO>,
    var FaqTitle: String,
    val FaqItems: List<FaqItemDTO>,
    val FeedbacksType: List<FeedbacksTypeDTO>,
    val Notifications: List<NotificationDTO> = emptyList(),
    val Count: String?
)