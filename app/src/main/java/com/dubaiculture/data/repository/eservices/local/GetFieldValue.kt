package com.dubaiculture.data.repository.eservices.local


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
data class FieldValue(
    val arabic: String,
    val english: String,
    val fieldName: String,
    val fieldType: String,
    val fieldValue: List<FieldValue>,
    val formName: String,
    val id: Int,
    val valueType: String
)