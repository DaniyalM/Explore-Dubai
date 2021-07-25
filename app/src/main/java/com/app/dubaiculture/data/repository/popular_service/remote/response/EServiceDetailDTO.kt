package com.app.dubaiculture.data.repository.popular_service.remote.response

data class EServiceDetailDTO(
    val Category: String?="",
    val DescriptionDTO: List<DescriptionDTO>?= emptyList(),
    val EnquireNumber: String?="",
    val FAQDTOS: List<FAQDTO>?= emptyList(),
    val paymentDTOS: List<PaymentDTO>?= emptyList(),
    val ProcedureDTO: List<ProcedureDTO>?= emptyList(),
    val RequiredDocumentDTO: List<RequiredDocumentDTO>?= emptyList(),
    val StartServiceText: String?="",
    val StartServiceUrl: String,
    val TermsAndConditionDTO: List<TermsAndConditionDTO>?= emptyList()
)