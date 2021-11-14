package com.dubaiculture.data.repository.popular_service.remote.response

data class FAQDTO(
    val FAQs: List<FAQXDTO>?= mutableListOf(),
    val FAQsTitle: String?
)