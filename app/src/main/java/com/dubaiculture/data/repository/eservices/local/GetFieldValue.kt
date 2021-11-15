package com.dubaiculture.data.repository.eservices.local

import com.dubaiculture.data.repository.eservices.remote.response.FieldValue
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponseDTOItem


data class GetFieldValueItem(
    val arabic: String,
    val english: String,
    val fieldName: String,
    val fieldType: String,
    val fieldValue: List<FieldValue>,
    val formName: String,
    val id: Int,
    val valueType: String
)
