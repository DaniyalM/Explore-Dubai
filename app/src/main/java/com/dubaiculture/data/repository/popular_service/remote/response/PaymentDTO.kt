package com.dubaiculture.data.repository.popular_service.remote.response

data class PaymentDTO(
    val AmountTitle: String?,
    val DescriptionTitle: String?,
    val PaymentTitle: String?,
    val Payments: List<PaymentXDTO>?= mutableListOf(),
    val TypeTitle: String?
)