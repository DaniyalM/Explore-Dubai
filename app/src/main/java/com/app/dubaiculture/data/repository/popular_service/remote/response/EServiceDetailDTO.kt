package com.app.dubaiculture.data.repository.popular_service.remote.response

data class EServiceDetailDTO(
    val Category: String?="",
    val Description: List<DescriptionDTO>?= emptyList(),
    val EnquireNumber: String?="",
    val FAQs: List<FAQDTO>?= emptyList(),
    val Payments: List<PaymentDTO>?= emptyList(),
    val Procedure: List<ProcedureDTO>?= emptyList(),
    val RequiredDocument: List<RequiredDocumentDTO>?= emptyList(),
    val StartServiceText: String?="",
    val StartServiceUrl: String,
    val TermsAndCondition: List<TermsAndConditionDTO>?= emptyList()
)