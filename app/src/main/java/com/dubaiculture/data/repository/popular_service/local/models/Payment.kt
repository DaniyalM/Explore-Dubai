package com.dubaiculture.data.repository.popular_service.local.models

data class Payment(
    val amountTitle: String,
    val descriptionTitle: String,
    val paymentTitle: String,
    val payments: List<PaymentX>,
    val typeTitle: String
)