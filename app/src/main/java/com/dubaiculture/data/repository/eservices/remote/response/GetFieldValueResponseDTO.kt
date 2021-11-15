package com.dubaiculture.data.repository.eservices.remote.response

//class GetFieldValueResponseDTO : ArrayList<GetFieldValueResponseDTOItem>()

data class GetFieldValueResponseDTOItem(
    val Arabic: String?=null,
    val English: String?=null,
    val FieldName: String?=null,
    val FieldType: String?=null,
    val FieldValue: List<FieldValueDTO>?=null,
    val FormName: String?=null,
    val ID: Int?=null,
    val ValueType: String?=null
)

data class FieldValueDTO(
    val Arabic: String?=null,
    val English: String?=null,
    val FieldName: String?=null,
    val FieldType: String?=null,
    val OptionValues: String?=null,
    val FormName: String?=null,
    val ID: Int?=null,
    val ValueType: String?=null
)