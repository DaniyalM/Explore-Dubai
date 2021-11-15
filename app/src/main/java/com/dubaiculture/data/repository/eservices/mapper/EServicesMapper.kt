package com.dubaiculture.data.repository.eservices.mapper

import com.dubaiculture.data.repository.eservices.local.FieldValueItem
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.response.FieldValueDTO
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponseDTOItem

fun transformFieldValueRequest(getFieldValueRequest: GetFieldValueRequest) =
    GetFieldValueRequestDTO(
        FormName = getFieldValueRequest.formName
    )

fun transformFieldValuesResponse(getFieldValueResponseDTOItem: GetFieldValueResponseDTOItem) =
    GetFieldValueItem(
        arabic = getFieldValueResponseDTOItem.Arabic,
        english = getFieldValueResponseDTOItem.English,
        fieldName = getFieldValueResponseDTOItem.FieldName,
        fieldType = getFieldValueResponseDTOItem.FieldType,
        fieldValue = getFieldValueResponseDTOItem.FieldValue.map { transformFieldValues(it) },
        formName = getFieldValueResponseDTOItem.FormName,
        id = getFieldValueResponseDTOItem.ID,
        valueType = getFieldValueResponseDTOItem.ValueType

    )

fun transformFieldValues(fieldValueDTO: FieldValueDTO) =
    FieldValueItem(
        arabic = fieldValueDTO.Arabic,
        english = fieldValueDTO.English,
        fieldName = fieldValueDTO.FieldName,
        fieldType = fieldValueDTO.FieldType,
        optionValues = fieldValueDTO.OptionValues,
        formName = fieldValueDTO.FormName,
        id = fieldValueDTO.ID,
        valueType = fieldValueDTO.ValueType

    )