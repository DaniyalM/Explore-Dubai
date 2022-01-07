package com.dubaiculture.data.repository.eservices.local

import android.os.Parcelable
import com.dubaiculture.data.repository.eservices.remote.response.ValidationDTO
import kotlinx.parcelize.Parcelize


@Parcelize
data class GetFieldValueItem(
    val arabic: String,
    val english: String,
    val fieldName: String,
    val fieldType: String,
    val fieldValue: List<FieldValueItem>,
    val formName: String,
    val id: Int,
    val valueType: String,
    val isRequired: Boolean,
    val hint_en: String,
    val hint_ar: String,
    val validations: List<Validation>,
    val defaultValue: String = ""
) : Parcelable

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
) : Parcelable

@Parcelize
data class Validation(
    val englishMsg: String,
    val arabicMsg: String,
    val errorCode: String,
    val pattern: String,
    val validationType: String
) : Parcelable