package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TermsAndCondition(
    val termsAndConditionsSummary: String,
    val termsAndConditionsTitle: String,
    val enquireNumber: String,
    val email: String,
    val serviceStart: String,
    val phoneHeading: String,
    val phoneNumber: String,
    val emailHeading: String,
    val emailAddress: String,
    val startServiceUrl: String,
    val id: String,
):Parcelable