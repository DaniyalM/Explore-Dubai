package com.dubaiculture.data.repository.eservices.local


data class GetFieldValueItem(
    val arabic: String,
    val english: String,
    val fieldName: String,
    val fieldType: String,
    val fieldValue: List<FieldValueItem>,
    val formName: String,
    val id: Int,
    val valueType: String,
    val selectedValue: String? = ""
)


data class FieldValueItem(
    val arabic: String,
    val english: String,
    val fieldName: String,
    val fieldType: String,
    val optionValues: String,
    val formName: String,
    val id: Int,
    val valueType: String
)
