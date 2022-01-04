package com.dubaiculture.data.repository.popular_service.remote.response

data class EServiceDetailDTO(
    val Category: String? = "",
        val Description: List<DescriptionDTO>? = mutableListOf(),
    val EnquireNumber: String? = "",
    val ID: String? = "",

    val PhoneHeading: String? = "",
    val PhoneNumber: String? = "",
    val EmailHeading: String? = "",
    val EmailAddress: String? = "",

    val FAQs: List<FAQDTO>? = mutableListOf(),
    val Payments: List<PaymentDTO>? = mutableListOf(),
    val Procedure: List<ProcedureDTO>? = mutableListOf(),
    val RequiredDocument: List<RequiredDocumentDTO>? = mutableListOf(),
    val StartServiceText: String? = "",
    val StartServiceUrl: String? = "",
    val IsFavourite: Boolean?,
    val TermsAndCondition: List<TermsAndConditionDTO>? = mutableListOf()
)