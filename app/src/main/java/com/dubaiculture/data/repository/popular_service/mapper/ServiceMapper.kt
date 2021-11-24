package com.dubaiculture.data.repository.popular_service.mapper

import com.dubaiculture.data.repository.more.local.FaqItem
import com.dubaiculture.data.repository.popular_service.local.models.*
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequestDTO
import com.dubaiculture.data.repository.popular_service.remote.response.EServiceDTO
import com.dubaiculture.data.repository.popular_service.remote.response.EServiceDetailDTO
import com.dubaiculture.data.repository.popular_service.remote.response.ServiceCategoryDTO
import com.dubaiculture.data.repository.popular_service.remote.response.ServiceResponse


fun transformServiceRequest(serviceRequest: EServiceRequest) = EServiceRequestDTO(
    FullName = serviceRequest.fullName ?: "",
    Email = serviceRequest.email ?: "",
    Comment = serviceRequest.comment ?: "",
    ItemID = serviceRequest.id ?: "",
    Culture = serviceRequest.culture ?: "",
)

fun transformService(serviceResponse: ServiceResponse): EServices =
    serviceResponse.Result.run {
        EServices(
            eServices = transformService(EServices),
            serviceCategory = transformServiceCategorys(ServiceCategory),
            title = Title,
            heading = Heading
        )
    }

fun transformService(eServices: List<EServiceDTO>): List<EService> =
    eServices.map {
        EService(
            category = it.Category,
            title = it.Title,
            summary = it.Summary,
            id = it.ID,
            categoryId = it.CategoryID
        )
    }

fun transformServiceCategorys(serviceCategory: List<ServiceCategoryDTO>): List<ServiceCategory> =
    serviceCategory.map {
        ServiceCategory(
            title = it.Title,
            id = it.ID,
            normalIcon = it.NormalIcon,
            hoverIcon = it.HoverIcon
        )
    }


fun transformServiceDetail(eServiceDetailDTO: EServiceDetailDTO): EServicesDetail =
    EServicesDetail(
        is_favourite = eServiceDetailDTO.IsFavourite ?: false,
        category = eServiceDetailDTO.Category ?: "",
        description = eServiceDetailDTO.Description?.map {
            Description(
                classification = it.Classification ?: "",
                classificationTitle = it.ClassificationTitle ?: "",
                descriptions = it.Descriptions ?: "",
                documentLink = it.DocumentLink ?: "",
                fileIcon = it.FileIcon ?: "",
                fileName = it.FileName ?: "",
                fileSize = it.FileSize ?: "",
                iD = it.ID ?: "",
                serviceChannelTitle = it.ServiceChannelTitle ?: "",
                serviceChannelIcons = it.ServiceChannelIcons ?: mutableListOf(),
                title = it.Title ?: "",
                type = it.Type ?: "",
                typeTitle = it.TypeTitle ?: "",
                startServiceText = eServiceDetailDTO.StartServiceText ?: ""
            )
        } ?: mutableListOf(),
        enquireNumber = eServiceDetailDTO.EnquireNumber ?: "",
        fAQs = eServiceDetailDTO.FAQs?.map {
            FAQ(
                fAQs = it.FAQs?.mapIndexed { index, faqxdto ->
                    FaqItem(
                        is_expanded = index == 0,
                        id = index + 1,
                        answer = faqxdto.Answer ?: "",
                        question = faqxdto.Question ?: ""
                    )
                } ?: mutableListOf(),
                fAQsTitle = it.FAQsTitle ?: ""
            )
        } ?: mutableListOf(),
        payments = eServiceDetailDTO.Payments?.map {
            Payment(
                amountTitle = it.AmountTitle ?: "",
                descriptionTitle = it.DescriptionTitle ?: "",
                paymentTitle = it.PaymentTitle ?: "",
                payments = it.Payments?.map {
                    PaymentX(
                        amount = it.Amount ?: "",
                        description = it.Description ?: "",
                        summary = it.Summary ?: "",
                        type = it.Type ?: ""

                    )
                } ?: mutableListOf(),
                typeTitle = it.TypeTitle ?: "",
            )
        } ?: mutableListOf(),
        procedure = eServiceDetailDTO.Procedure?.map {
            Procedure(
                serviceProcedure = it.ServiceProcedure?.mapIndexed { index, serviceProcedureDTO ->
                    ServiceProcedure(
                        summary = serviceProcedureDTO.Summary ?: "",
                        title = serviceProcedureDTO.Title ?: "",
                        id = index + 1
                    )
                } ?: mutableListOf(),
                serviceProcedureTitle = it.ServiceProcedureTitle ?: ""
            )
        } ?: mutableListOf(),
        requiredDocument = eServiceDetailDTO.RequiredDocument?.map {
            RequiredDocument(
                requiredDocuments = it.RequiredDocuments ?: mutableListOf(),
                requiredDocumentsTitle = it.RequiredDocumentsTitle ?: ""
            )
        } ?: mutableListOf(),
        startServiceText = eServiceDetailDTO.StartServiceText ?: "",
        startServiceUrl = eServiceDetailDTO.StartServiceUrl ?: "",
        termsAndCondition = eServiceDetailDTO.TermsAndCondition?.map {
            TermsAndCondition(
                termsAndConditionsSummary = it.TermsAndConditionsSummary ?: "",
                termsAndConditionsTitle = it.TermsAndConditionsTitle ?: "",
                enquireNumber = eServiceDetailDTO.EnquireNumber ?: "",
                email = "",
                serviceStart = eServiceDetailDTO.StartServiceText ?: "",
                phoneHeading = eServiceDetailDTO.PhoneHeading ?: "",
                phoneNumber = eServiceDetailDTO.PhoneNumber ?: "",
                emailHeading = eServiceDetailDTO.EmailHeading ?: "",
                emailAddress = eServiceDetailDTO.EmailAddress ?: "",
                id = eServiceDetailDTO.ID ?: ""
            )
        } ?: mutableListOf()


    )


