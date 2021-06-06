package com.app.dubaiculture.data.repository.more.local

import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink

data class ContactCenter(
    var title: String,
    var description: String,
    var contactCenterLocation: ContactCenterLocation,
    var contactCenterReach: ContactCenterReach,
    var contactCenterFeedback: ContactCenterFeedback,
    var contactCenterSuggestionComplains: ContactCenterSuggestionComplains,
    var socialLinks: List<SocialLink>

)
