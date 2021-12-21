package com.dubaiculture.ui.postLogin.eservices

import android.app.ActionBar
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.content.res.AppCompatResources
import com.dubaiculture.R
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.ui.components.customEditText.CustomEditText
import com.dubaiculture.ui.components.customtextview.CustomTextView
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

object FieldUtils {

    fun createTextView(context: Context, fieldValueItem: GetFieldValueItem): CustomTextView {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val textView = CustomTextView(context)
        textView.id = fieldValueItem.id
        textView.text = fieldValueItem.english
        textView.layoutParams = layoutParams
        return textView
    }

    fun createEditText(
        fieldValue: GetFieldValueItem,
        context: Context,
        fieldListener: FieldListener
    ): CustomEditText {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val editText = CustomEditText(context)

        editText.id = fieldValue.id
        editText.layoutParams = layoutParams
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
}