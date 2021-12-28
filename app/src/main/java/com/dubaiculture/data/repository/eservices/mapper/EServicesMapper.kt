package com.dubaiculture.data.repository.eservices.mapper

import com.dubaiculture.data.repository.eservices.local.FieldValueItem
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.local.Validation
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequest
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.response.FieldValueDTO
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponseDTOItem
import com.dubaiculture.data.repository.eservices.remote.response.ValidationDTO
import com.dubaiculture.ui.postLogin.events.detail.helper.MultipartFormHelper.getMultiPartData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

fun transformNocRequest(nocRequest: CreateNocRequest) = CreateNocRequestDTO(
    UserType = nocRequest.userType.toRequestBody("text/plain".toMediaType()),
    Subject = nocRequest.subject.toRequestBody("text/plain".toMediaType()),
    FilmingDate = nocRequest.filmingDate.toRequestBody("text/plain".toMediaType()),
    FromTime = nocRequest.fromTime.toRequestBody("text/plain".toMediaType()),
    ToTime = nocRequest.toTime.toRequestBody("text/plain".toMediaType()),
    ContactPhoneNumber = nocRequest.contactPhoneNumber.toRequestBody("text/plain".toMediaType()),
    FullName = nocRequest.fullName.toRequestBody("text/plain".toMediaType()),
    LocationAddress = nocRequest.locationAddress.toRequestBody("text/plain".toMediaType()),
    UserEmailID = nocRequest.userEmailID.toRequestBody("text/plain".toMediaType()),
    CompanyDepartment = nocRequest.companyDepartment.toRequestBody("text/plain".toMediaType()),
    Signature = getMultiPartData(nocRequest.signature),
    Status = nocRequest.status.toRequestBody("text/plain".toMediaType()),
    StatusComments = nocRequest.statusComments.toRequestBody("text/plain".toMediaType())
)

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