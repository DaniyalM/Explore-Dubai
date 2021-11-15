package com.dubaiculture.data.repository.eservices.remote.response

//class GetFieldValueResponseDTO : ArrayList<GetFieldValueResponseDTOItem>()

data class GetFieldValueResponseDTOItem(
    val Arabic: String,
    val English: String,
    val FieldName: String,
    val FieldType: String,
    val FieldValue: List<FieldValue>,
    val FormName: String,
    val ID: Int,
    val ValueType: String
)

data class FieldValue(
    val Arabic: String,
    val English: String,
    val FieldName: String,
    val FieldType: String,
    val FieldValue: List<FieldValue>,
    val FormName: String,
    val ID: Int,
    val ValueType: String
)