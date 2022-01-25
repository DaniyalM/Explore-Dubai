package com.dubaiculture.ui.postLogin.survey

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dubaiculture.data.repository.survey.request.Items

object SurveyFieldUtils {

//    fun createEditTextWithLabel(
//        isArabic: Boolean,
//        layoutInflater: LayoutInflater,
//        root: ViewGroup,
//        fieldValueItem: GetFieldValueItem
//    ) {
//        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
//        root.addView(label)
//        val et = createEditText(isArabic, layoutInflater, root, fieldValueItem)
//        root.addView(et)
//    }
//
//
//    fun createEditText(
//        isArabic: Boolean,
//        layoutInflater: LayoutInflater,
//        root: ViewGroup,
//        fieldValueItem: Items,
//    ): CustomEditText {
//        val view = EserviceEditTextBinding.inflate(layoutInflater, root, false)
//        val editText = view.editText
//
//        editText.id = fieldValueItem.id
//        editText.hint = if (isArabic) fieldValueItem.hint_ar else fieldValueItem.hint_en
//        editText.setText(fieldValueItem.defaultValue)
//        if (fieldValueItem.valueType == ValueType.INPUT_TEXT_MULTILINE.valueType) {
//            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
//        }
//        return editText
//    }
}