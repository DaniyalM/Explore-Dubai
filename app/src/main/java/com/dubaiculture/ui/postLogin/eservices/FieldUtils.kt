package com.dubaiculture.ui.postLogin.eservices

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceDropDownBinding
import com.dubaiculture.databinding.EserviceEditTextBinding
import com.dubaiculture.databinding.EserviceTextViewBinding
import com.dubaiculture.ui.components.customEditText.CustomEditText
import com.dubaiculture.utils.*
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import java.text.SimpleDateFormat
import java.util.*

object FieldUtils {

    fun createTextView(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
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
        root: ViewGroup,
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
        root: ViewGroup,
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

    fun createDateField(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: (date: String) -> Unit
    ): CustomEditText {
        val editText = createEditText(layoutInflater, root, fieldValueItem)
        editText.isFocusable = false
        editText.setOnClickListener {
            DatePickerUtil(
                selectedDate = editText.text.toString(),
                selectedDateFormat = Constants.DateFormats.MM_DD_YYYY,
                root.context,
                object : DatePickerUtil.DatePickerInterface {
                    override fun onDateSelected(calendar: Calendar) {
                        val date: Date = calendar.time
                        val format = Constants.DateFormats.MM_DD_YYYY
                        val str = date.toString(format)
                        editText.setText(str)
                        callback(str)
                    }
                }
            ).showPicker()
        }
        editText.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(root.context, R.drawable.calender),
            null
        )
        return editText
    }

    fun createTimeField(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fragmentManager: FragmentManager,
        fieldValueItem: GetFieldValueItem,
        callback: (date: String) -> Unit
    ): CustomEditText {
        val editText = createEditText(layoutInflater, root, fieldValueItem)
        editText.isFocusable = false
        val cal = Calendar.getInstance()

        val selectedTime = editText.text.toString()

        if (selectedTime.isNotEmpty()) {
            val sdf = SimpleDateFormat(Constants.DateFormats.HH_MM_A, Locale.ENGLISH)
            cal.time = sdf.parse(selectedTime)
        }

        editText.setOnClickListener {
            JustTimePicker(root.context, object : JustTimePicker.TimePickerInterface {
                override fun onTimeSelected(calendar: Calendar) {
                    val df = SimpleDateFormat(Constants.DateFormats.HH_MM_A)
                    editText.setText(df.format(calendar.time))
                }
            }, fragmentManager, cal).showPicker()

        }
        editText.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(root.context, R.drawable.calender),
            null
        )
        return editText
    }

    fun createFileField(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: () -> Unit
    ): CustomEditText {
        val editText = createEditText(layoutInflater, root, fieldValueItem)
        editText.isFocusable = false
        editText.setOnClickListener {
            callback()
        }
        editText.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(root.context, R.drawable.ic_file),
            null
        )
        return editText
    }

    fun createImageField(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem
    ): CustomEditText {
        val editText = createEditText(layoutInflater, root, fieldValueItem)
        editText.isFocusable = false
        editText.setOnClickListener {

        }
        editText.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(root.context, R.drawable.image_placeholder),
            null
        )
        return editText
    }

}