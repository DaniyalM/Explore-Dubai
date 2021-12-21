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
        fieldValue: GetFieldValueItem,
        fieldListener: FieldListener
    ): CustomEditText {
        val view = EserviceEditTextBinding.inflate(layoutInflater, root, false)
        val editText = view.editText

        editText.id = fieldValue.id
        editText.hint = fieldValue.english
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                fieldListener.fetchInput(
                    fieldValue.copy(selectedValue = s.toString().trim())
                )
            }
        })
        return editText
    }

    fun createDropDown(context: Context, fieldValueItem: GetFieldValueItem): AutoCompleteTextView {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val dropDown = AutoCompleteTextView(context, null)

        dropDown.id = fieldValueItem.id
        dropDown.hint = fieldValueItem.english
        dropDown.layoutParams = layoutParams

        dropDown.setAdapter(
            ArrayAdapter(
                context,
                android.R.layout.simple_dropdown_item_1line,
                fieldValueItem.fieldValue.map {
                    it.english
                }
            )
        )
        dropDown.focusable = View.NOT_FOCUSABLE
        dropDown.setOnClickListener {
            dropDown.showDropDown()
        }
        dropDown.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(context, R.drawable.arrow_readmore_),
            null
        )
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