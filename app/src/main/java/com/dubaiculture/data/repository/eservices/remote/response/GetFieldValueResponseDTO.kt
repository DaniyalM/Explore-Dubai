package com.dubaiculture.data.repository.eservices.remote.response

//class GetFieldValueResponseDTO : ArrayList<GetFieldValueResponseDTOItem>()

data class GetFieldValueResponseDTOItem(
    val Arabic: String,
    val English: String,
    val FieldName: String,
    val FieldType: String,
    val FieldValue: List<FieldValueResponseDTO>,
    val FormName: String,
    val ID: Int,
    val ValueType: String
)

data class FieldValueResponseDTO(
    val Arabic: String,
    val English: String,
    val FieldName: String,
    val FieldType: String,
    val FieldValue: List<FieldValueResponseDTO>,
    val FormName: String,
    val ID: Int,
    val ValueType: String
)