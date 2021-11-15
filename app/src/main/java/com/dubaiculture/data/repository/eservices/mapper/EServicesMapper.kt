package com.dubaiculture.data.repository.eservices.mapper

import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequest
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponseDTOItem

fun transformFieldValueRequest(getFieldValueRequest: GetFieldValueRequest) =
    GetFieldValueRequestDTO(
        FormName = getFieldValueRequest.formName
    )

fun transformFieldValues(getFieldValueResponseDTOItem: GetFieldValueResponseDTOItem) =
    GetFieldValueItem(
        arabic = getFieldValueResponseDTOItem.Arabic,
        english = getFieldValueResponseDTOItem.English,
        fieldName = getFieldValueResponseDTOItem.FieldName,
        fieldType = getFieldValueResponseDTOItem.FieldType,
        fieldValue = getFieldValueResponseDTOItem.FieldValue,
        formName = getFieldValueResponseDTOItem.FormName,
        id = getFieldValueResponseDTOItem.ID,
        valueType = getFieldValueResponseDTOItem.ValueType

    )