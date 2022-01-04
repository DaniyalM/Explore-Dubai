package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentX(
    val amount: String,
    val description: String,
    val summary: String,
    val type: String
):Parcelable