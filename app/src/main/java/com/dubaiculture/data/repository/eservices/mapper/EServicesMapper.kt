package com.dubaiculture.data.repository.eservices.mapper

import com.dubaiculture.data.repository.eservices.local.*
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequest
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.response.*
import com.dubaiculture.ui.postLogin.events.detail.helper.MultipartFormHelper.getMultiPartData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

fun transformFieldValueRequest(getFieldValueRequest: GetFieldValueRequest) =
    GetFieldValueRequestDTO(
        FormName = getFieldValueRequest.formName
    )

fun transformFieldValuesResponse(
    getFieldValueResponseDTOItem: GetFieldValueResponseDTOItem
) =
    GetFieldValueItem(
        arabic = getFieldValueResponseDTOItem.Arabic ?: "",
        english = getFieldValueResponseDTOItem.English ?: "",
        fieldName = getFieldValueResponseDTOItem.FieldName ?: "",
        fieldType = getFieldValueResponseDTOItem.FieldType ?: "",
        fieldValue = getFieldValueResponseDTOItem.FieldValue?.map { transformFieldValues(it) }
            ?: mutableListOf(),
        validations = getFieldValueResponseDTOItem.validations?.map { transformValidations(it) }
            ?: mutableListOf(),
        formName = getFieldValueResponseDTOItem.FormName ?: "",
        id = getFieldValueResponseDTOItem.ID ?: 0,
        valueType = getFieldValueResponseDTOItem.ValueType ?: "",
        isRequired = getFieldValueResponseDTOItem.isRequired ?: false,
        hint_en = getFieldValueResponseDTOItem.HintText_en ?: "",
        hint_ar = getFieldValueResponseDTOItem.HintText_AR ?: ""
    )

fun transformFieldValues(fieldValueDTO: FieldValueDTO) =
    FieldValueItem(
        arabic = fieldValueDTO.Arabic ?: "",
        english = fieldValueDTO.English ?: "",
        fieldName = fieldValueDTO.FieldName ?: "",
        fieldType = fieldValueDTO.FieldType ?: "",
        optionValues = fieldValueDTO.OptionValues ?: "",
        formName = fieldValueDTO.FormName ?: "",
        id = fieldValueDTO.ID ?: 0,
        valueType = fieldValueDTO.ValueType ?: ""
    )

fun transformValidations(validationDTO: ValidationDTO) =
    Validation(
        englishMsg = validationDTO.EnglishMsg ?: "",
        arabicMsg = validationDTO.ArabicMsg ?: "",
        errorCode = validationDTO.ErrorCode ?: "",
        pattern = validationDTO.Pattern ?: "",
        validationType = validationDTO.ValidationType ?: ""
    )

fun transformEServiceStatus(eServiceStatusDto: EServiceStatusDto) =
    EServiceStatus(
        id = eServiceStatusDto.ID,
        title = eServiceStatusDto.Title,
        categoryID = eServiceStatusDto.CategoryID,
        category = eServiceStatusDto.Category,
        summary = eServiceStatusDto.Summary,
        isFavourite = eServiceStatusDto.IsFavourite,
        startServiceText = eServiceStatusDto.StartServiceText,
        startServiceUrl = eServiceStatusDto.StartServiceUrl,
        formName = eServiceStatusDto.FormName,
        formSubmitURL = eServiceStatusDto.FormSubmitURL,
        request = transformEServiceStatusDetails(eServiceStatusDto.Request)

    )

fun transformEServiceStatusDetails(eServiceStatusDto: EServiceStatusDetailsDto) =
    EServiceStatusDetails(
        id = eServiceStatusDto.ID,
        dateTime = eServiceStatusDto.DateTime,
        status = eServiceStatusDto.Status
    )