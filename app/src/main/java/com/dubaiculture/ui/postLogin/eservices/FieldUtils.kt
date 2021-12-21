package com.dubaiculture.ui.postLogin.eservices

import android.app.ActionBar
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.dubaiculture.R
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceDropDownBinding
import com.dubaiculture.databinding.EserviceEditTextBinding
import com.dubaiculture.databinding.EserviceTextViewBinding
import com.dubaiculture.ui.components.customEditText.CustomEditText
import com.dubaiculture.ui.components.customtextview.CustomTextView
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

object FieldUtils {

    fun createTextView(
        layoutInflater: LayoutInflater,
        root: ViewGroup?,
        fieldValueItem: GetFieldValueItem
    ): TextView {
        val view = EserviceTextViewBinding.inflate(layoutInflater, root, false)
        val textView = view.textView
        textView.id = fieldValueItem.id
        textView.text = fieldValueItem.english
        return textView
    }

    fun createEditText(
        layoutInflater: LayoutInflater,
        root: ViewGroup?,
        fieldValue: GetFieldValueItem
    ): CustomEditText {
        val view = EserviceEditTextBinding.inflate(layoutInflater, root, false)
        val editText = view.editText

        editText.id = fieldValue.id
        editText.hint = fieldValue.english

        return editText
    }

    fun createDropDown(
        layoutInflater: LayoutInflater,
        root: ViewGroup?,
        fieldValueItem: GetFieldValueItem
    ): AutoCompleteTextView {
        val view = EserviceDropDownBinding.inflate(layoutInflater, root, false)

        val dropDown = view.dropDown
        dropDown.id = fieldValueItem.id
        dropDown.hint = fieldValueItem.english

        dropDown.setAdapter(
            ArrayAdapter(
                view.root.context,
                android.R.layout.simple_dropdown_item_1line,
                fieldValueItem.fieldValue.map {
                    it.english
                }
            )
        )
        dropDown.setOnClickListener {
            dropDown.showDropDown()
        }
        return dropDown
    }

    fun createDateField(context: Context, fieldValueItem: GetFieldValueItem): CustomTextView {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val textView = CustomTextView(context)
        textView.id = fieldValueItem.id
        textView.text = fieldValueItem.english
        textView.layoutParams = layoutParams
        textView.setOnClickListener {

        }
        textView.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(context, R.drawable.calender),
            null
        )
        return textView
    }

    fun createFileField(context: Context, fieldValueItem: GetFieldValueItem): CustomTextView {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val textView = CustomTextView(context)
        textView.id = fieldValueItem.id
        textView.text = fieldValueItem.english
        textView.layoutParams = layoutParams
        textView.setOnClickListener {

        }
        textView.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(context, R.drawable.ic_file),
            null
        )
        return textView
    }

    fun createImageField(context: Context, fieldValueItem: GetFieldValueItem): CustomTextView {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val textView = CustomTextView(context)
        textView.id = fieldValueItem.id
        textView.text = fieldValueItem.english
        textView.layoutParams = layoutParams
        textView.setOnClickListener {

        }
        textView.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(context, R.drawable.image_placeholder),
            null
        )
        return textView
    }

}