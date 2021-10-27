package com.app.dubaiculture.data.repository.popular_service.local.models

import com.app.dubaiculture.data.repository.more.local.FaqItem

data class FAQ(
    val fAQs: List<FaqItem>,
    val fAQsTitle: String
)