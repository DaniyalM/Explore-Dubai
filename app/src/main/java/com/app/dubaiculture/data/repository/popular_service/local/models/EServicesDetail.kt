package com.app.dubaiculture.data.repository.popular_service.local.models

import com.app.dubaiculture.data.repository.popular_service.remote.response.*

data class EServicesDetail(
    val category: String?="",
    val description: List<Description>?= emptyList(),
    val enquireNumber: String?="",
    val fAQs: List<FAQ>?= emptyList(),
    val payments: List<Payment>?= emptyList(),
    val procedure: List<Procedure>?= emptyList(),
    val requiredDocument: List<RequiredDocument>?= emptyList(),
    val startServiceText: String?="",
    val startServiceUrl: String?="",
    val termsAndCondition: List<TermsAndCondition>?= emptyList()
)