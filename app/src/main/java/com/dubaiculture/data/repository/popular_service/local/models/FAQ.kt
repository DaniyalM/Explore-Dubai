package com.dubaiculture.data.repository.popular_service.local.models

import com.dubaiculture.data.repository.more.local.FaqItem

data class FAQ(
    val fAQs: List<FaqItem>,
    val fAQsTitle: String
)