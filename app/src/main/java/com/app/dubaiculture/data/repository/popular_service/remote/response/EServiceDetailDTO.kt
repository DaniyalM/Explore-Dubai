package com.app.dubaiculture.data.repository.popular_service.remote.response

data class EServiceDetailDTO(
    val Category: String?="",
    val Description: List<DescriptionDTO>?= mutableListOf(),
    val EnquireNumber: String?="",
    val FAQs: List<FAQDTO>?= mutableListOf(),
    val Payments: List<PaymentDTO>?= mutableListOf(),
    val Procedure: List<ProcedureDTO>?= mutableListOf(),
    val RequiredDocument: List<RequiredDocumentDTO>?= mutableListOf(),
    val StartServiceText: String?="",
    val StartServiceUrl: String?="",
    val TermsAndCondition: List<TermsAndConditionDTO>?= mutableListOf()
)