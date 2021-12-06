package com.dubaiculture.ui.postLogin.eservices

import android.app.ActionBar
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.ui.components.customtextview.CustomTextView
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

object FieldUtils {

    fun createTextView(context: Context, fieldValueItem: GetFieldValueItem): TextView {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val textView = CustomTextView(context, null)
        textView.id = fieldValueItem.id
        textView.text = fieldValueItem.english
        textView.layoutParams = layoutParams

        return textView

    }

     fun createEditText(
        fieldValue: GetFieldValueItem,
        context: Context,
        fieldListener: FieldListener
    ): EditText {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val editText = EditText(context)
        editText.id = fieldValue.id
        editText.layoutParams = layoutParams
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
}