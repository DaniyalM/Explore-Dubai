package com.dubaiculture.data.repository.eservices.remote.response

data class GetFieldValueResponseDTOItem(
    val ID: Int? = null,
    val FormName: String? = null,
    val FieldName: String? = null,
    val English: String? = null,
    val Arabic: String? = null,
    val FieldType: String? = null,
    val ValueType: String? = null,
    val FieldValue: List<FieldValueDTO>? = null,
    val HintText_AR: String? = null,
    val HintText_en: String? = null,
    val isRequired: Boolean? = null,
    val validations: List<ValidationDTO>? = null
)

data class FieldValueDTO(
    val Arabic: String? = null,
    val English: String? = null,
    val FieldName: String? = null,
    val FieldType: String? = null,
    val OptionValues: String? = null,
    val FormName: String? = null,
    val ID: Int? = null,
    val ValueType: String? = null
)

data class ValidationDTO(
    val EnglishMsg: String? = null,
    val ArabicMsg: String? = null,
    val ErrorCode: String? = null,
    val Pattern: String? = null,
    val ValidationType: String? = null
)