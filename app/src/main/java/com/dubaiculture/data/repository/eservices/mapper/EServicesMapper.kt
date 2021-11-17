package com.dubaiculture.data.repository.eservices.mapper

import com.dubaiculture.data.repository.eservices.local.FieldValueItem
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequest
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.response.FieldValueDTO
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponseDTOItem
import com.estimote.coresdk.cloud.model.User
import okhttp3.MultipartBody


fun transformNocRequest(nocRequest: CreateNocRequest)=CreateNocRequestDTO(
    Title = nocRequest.title,
    UserType = nocRequest.userType,
    Subject = nocRequest.subject,
    FilmingDate = nocRequest.filmingDate,
    FromTime = nocRequest.fromTime,
    ToTime = nocRequest.toTime,
    ContactPhoneNumber = nocRequest.contactPhoneNumber,
    FullName = nocRequest.fullName,
    LocationAddress = nocRequest.locationAddress,
    UserEmailID = nocRequest.userEmailID,
    countryCode = nocRequest.countryCode,
    file = nocRequest.file
)
//val Title: String,
//val UserType: String,
//val Subject: String,
//val FilmingDate: String,
//val FromTime: String,
//val ToTime: String,
//val ContactPhoneNumber: String,
//val FullName: String,
//val LocationAddress: String,
//val UserEmailID: String,
//val countryCode: String,
//val file: MultipartBody.Part

fun transformFieldValueRequest(getFieldValueRequest: GetFieldValueRequest) =
    GetFieldValueRequestDTO(
        FormName = getFieldValueRequest.formName
    )

fun transformFieldValuesResponse(index:Int,getFieldValueResponseDTOItem: GetFieldValueResponseDTOItem) =
    GetFieldValueItem(
        index=index,
        arabic = getFieldValueResponseDTOItem.Arabic?:"",
        english = getFieldValueResponseDTOItem.English?:"",
        fieldName = getFieldValueResponseDTOItem.FieldName?:"",
        fieldType = getFieldValueResponseDTOItem.FieldType?:"",
        fieldValue = getFieldValueResponseDTOItem.FieldValue?.map { transformFieldValues(it) }?: mutableListOf(),
        formName = getFieldValueResponseDTOItem.FormName?:"",
        id = getFieldValueResponseDTOItem.ID?:0,
        valueType = getFieldValueResponseDTOItem.ValueType?:"",
        selectedValue = null

    )

fun transformFieldValues(fieldValueDTO: FieldValueDTO) =
    FieldValueItem(
        arabic = fieldValueDTO.Arabic?:"",
        english = fieldValueDTO.English?:"",
        fieldName = fieldValueDTO.FieldName?:"",
        fieldType = fieldValueDTO.FieldType?:"",
        optionValues = fieldValueDTO.OptionValues?:"",
        formName = fieldValueDTO.FormName?:"",
        id = fieldValueDTO.ID?:0,
        valueType = fieldValueDTO.ValueType?:""

    )