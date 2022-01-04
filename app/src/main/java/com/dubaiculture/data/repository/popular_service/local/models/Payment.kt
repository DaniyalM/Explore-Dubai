package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val amountTitle: String,
    val descriptionTitle: String,
    val paymentTitle: String,
    val payments: List<PaymentX>,
    val typeTitle: String
):Parcelable