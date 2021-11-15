package com.dubaiculture.data.repository.eservices.remote.response

//class GetFieldValueResponseDTO : ArrayList<GetFieldValueResponseDTOItem>()

data class GetFieldValueResponseDTOItem(
    val Arabic: String,
    val English: String,
    val FieldName: String,
    val FieldType: String,
    val FieldValue: List<FieldValueDTO>,
    val FormName: String,
    val ID: Int,
    val ValueType: String
)

data class FieldValueDTO(
    val Arabic: String,
    val English: String,
    val FieldName: String,
    val FieldType: String,
    val OptionValues: String,
    val FormName: String,
    val ID: Int,
    val ValueType: String
)