package com.app.dubaiculture.data.repository.popular_service.remote.response

data class PaymentDTO(
    val AmountTitle: String,
    val DescriptionTitle: String,
    val PaymentTitle: String,
    val payments: List<PaymentXDTO>,
    val TypeTitle: String
)