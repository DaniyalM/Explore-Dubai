package com.dubaiculture.data.repository.eservices.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GetFieldValueItem(
    val index: Int,
    val arabic: String,
    val english: String,
    val fieldName: String,
    val fieldType: String,
    val fieldValue: List<FieldValueItem>,
    val formName: String,
    val id: Int,
    val valueType: String,
    val selectedValue: String? = null,
    val notValid: Boolean = false
):Parcelable

@Parcelize
data class FieldValueItem(
    val arabic: String,
    val english: String,
    val fieldName: String,
    val fieldType: String,
    val optionValues: String,
    val formName: String,
    val id: Int,
    val valueType: String
):Parcelable
