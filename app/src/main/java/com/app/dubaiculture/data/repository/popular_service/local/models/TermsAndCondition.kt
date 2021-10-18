package com.app.dubaiculture.data.repository.popular_service.local.models

data class TermsAndCondition(
    val termsAndConditionsSummary: String,
    val termsAndConditionsTitle: String,
    val enquireNumber:String?="",
    val email:String?=""
)