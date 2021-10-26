package com.app.dubaiculture.data.repository.popular_service.local.models

import com.app.dubaiculture.data.repository.popular_service.remote.response.*

data class EServicesDetail(
    val category: String,
    val description: List<Description>,
    val enquireNumber: String,
    val fAQs: List<FAQ>,
    val payments: List<Payment>,
    val procedure: List<Procedure>,
    val requiredDocument: List<RequiredDocument>,
    val startServiceText: String,
    val startServiceUrl: String,
    val termsAndCondition: List<TermsAndCondition>,
    val is_favourite: Boolean
)