package com.dubaiculture.ui.postLogin.eservices.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceDropDownBinding
import com.dubaiculture.databinding.EserviceEditTextBinding
import com.dubaiculture.databinding.EserviceRadioGroupBinding
import com.dubaiculture.databinding.EserviceTextViewBinding
import com.dubaiculture.ui.components.customEditText.CustomEditText
import com.dubaiculture.ui.postLogin.eservices.enums.ValueType
import com.dubaiculture.utils.*
import java.text.SimpleDateFormat
import java.util.*

object FieldUtils {

    fun createTextViewWithDescription(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val desc = createTextDescription(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(desc)
    }

    fun createTextView(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem
    ): TextView {
        val view = EserviceTextViewBinding.inflate(layoutInflater, root, false)
        val textView = view.textView
        var text = if (isArabic) fieldValueItem.arabic else fieldValueItem.english
        if (fieldValueItem.isRequired) text += "*"
        textView.text = text
        textView.setColouredSpan("*", Color.RED)
        return textView
    }

    fun createTextDescription(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem
    ): TextView? {
        if (fieldValueItem.hint_en.isEmpty()) return null
        val view = EserviceTextViewBinding.inflate(layoutInflater, root, false)
        val textView = view.textView
        val text = if (isArabic) fieldValueItem.hint_ar else fieldValueItem.hint_en
        textView.text = text
        return textView
    }

    fun createEditTextWithLabel(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val et = createEditText(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(et)
    }

    fun createEditText(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
    ): CustomEditText {
        val view = EserviceEditTextBinding.inflate(layoutInflater, root, false)
        val editText = view.editText

        editText.id = fieldValueItem.id
        editText.hint = if (isArabic) fieldValueItem.hint_ar else fieldValueItem.hint_en
        editText.setText(fieldValueItem.defaultValue)
        if (fieldValueItem.valueType == ValueType.INPUT_TEXT_MULTILINE.valueType) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        }
        return editText
    }

    fun createDropDownWithLabel(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: (value: String) -> Unit
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val dd = createDropDown(isArabic, layoutInflater, root, fieldValueItem, callback)
        root.addView(dd)
    }

    fun createDropDown(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: (value: String) -> Unit
    ): AutoCompleteTextView {
        val view = EserviceDropDownBinding.inflate(layoutInflater, root, false)

        val dropDown = view.dropDown
        dropDown.id = fieldValueItem.id
        dropDown.hint = if (isArabic) fieldValueItem.hint_ar else fieldValueItem.hint_en

        dropDown.setAdapter(
            ArrayAdapter(
                view.root.context,
                android.R.layout.simple_dropdown_item_1line,
                fieldValueItem.fieldValue.map {
                    if (isArabic) it.arabic else it.english
                }
            )
        )
        dropDown.setOnClickListener {
            dropDown.showDropDown()
        }
        dropDown.setOnItemClickListener { parent, view, position, id ->
            callback(fieldValueItem.fieldValue[position].english)
        }
        return dropDown
    }

    fun createDateFieldWithLabel(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: (date: String) -> Unit
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val dd = createDateField(isArabic, layoutInflater, root, fieldValueItem, callback)
        root.addView(dd)
    }

    fun createDateField(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: (date: String) -> Unit
    ): CustomEditText {
        val editText = createEditText(isArabic, layoutInflater, root, fieldValueItem)
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
                        editText.setText(date.toString(format))
                        //convert date here
                        callback(date.toString(format, Locale.ENGLISH))
                    }
                }
            ).showPicker()
        }
        setDrawableEnd(
            isArabic,
            AppCompatResources.getDrawable(root.context, R.drawable.calender),
            editText
        )
        return editText
    }

    fun createTimeFieldWithLabel(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fragmentManager: FragmentManager,
        fieldValueItem: GetFieldValueItem,
        callback: (date: String) -> Unit
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val timeField = createTimeField(
            isArabic,
            layoutInflater,
            root,
            fragmentManager,
            fieldValueItem,
            callback
        )
        root.addView(timeField)
    }

    fun createTimeField(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fragmentManager: FragmentManager,
        fieldValueItem: GetFieldValueItem,
        callback: (date: String) -> Unit
    ): CustomEditText {
        val editText = createEditText(isArabic, layoutInflater, root, fieldValueItem)
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
                    val df_en = SimpleDateFormat(Constants.DateFormats.HH_MM_A, Locale.ENGLISH)
                    //convert date here
                    callback(df_en.format(calendar.time))
                    editText.setText(df.format(calendar.time))
                }
            }, fragmentManager, cal).showPicker()

        }
        setDrawableEnd(
            isArabic,
            AppCompatResources.getDrawable(root.context, R.drawable.calender),
            editText
        )
        return editText
    }

    fun createFileFieldWithLabel(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: () -> Unit
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val fd = createFileField(isArabic, layoutInflater, root, fieldValueItem, callback)
        root.addView(fd)
    }

    fun createFileField(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: () -> Unit
    ): CustomEditText {
        val editText = createEditText(isArabic, layoutInflater, root, fieldValueItem)
        editText.isFocusable = false
        editText.setOnClickListener {
            callback()
        }
        setDrawableEnd(
            isArabic,
            AppCompatResources.getDrawable(root.context, R.drawable.ic_file_eservice),
            editText
        )
        return editText
    }

    fun createImageFieldWithLabel(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val fd = createImageField(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(fd)
    }

    fun createImageField(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem
    ): CustomEditText {
        val editText = createEditText(isArabic, layoutInflater, root, fieldValueItem)
        editText.isFocusable = false
        editText.setOnClickListener {

        }
        setDrawableEnd(
            isArabic,
            AppCompatResources.getDrawable(root.context, R.drawable.gallery_),
            editText
        )
        return editText
    }

    fun createRadioButtonWithLabel(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: (value: String) -> Unit
    ) {
        val label = createTextView(isArabic, layoutInflater, root, fieldValueItem)
        root.addView(label)
        val rb = createRadioButton(isArabic, layoutInflater, root, fieldValueItem, callback)
        root.addView(rb)
    }

    fun createRadioButton(
        isArabic: Boolean,
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: GetFieldValueItem,
        callback: (value: String) -> Unit
    ): RadioGroup {
        val view = EserviceRadioGroupBinding.inflate(layoutInflater, root, false)
        fieldValueItem.fieldValue.elementAtOrNull(0)?.let {
            view.rb1.id = it.id
            view.rb1.text = if (isArabic) it.arabic else it.english
        }
        fieldValueItem.fieldValue.elementAtOrNull(1).let {
            if (it == null) {
                view.rb2.hide()
            } else {
                view.rb2.id = it.id
                view.rb2.text = if (isArabic) it.arabic else it.english
            }
        }
        val group = view.radioGroup
        group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                view.rb1.id -> {
                    callback(
//                        view.rb1.text.toString()
                        fieldValueItem.fieldValue.elementAtOrNull(0)?.english ?: ""
                    )
                }
                view.rb2.id -> {
                    callback(
//                        view.rb2.text.toString()
                        fieldValueItem.fieldValue.elementAtOrNull(1)?.english ?: ""
                    )
                }
            }
        }

        return group
    }

    private fun setDrawableEnd(isArabic: Boolean, drawable: Drawable?, editText: EditText) {
        if (isArabic)
            editText.setCompoundDrawablesWithIntrinsicBounds(
                drawable,
                null,
                null,
                null
            ) else editText.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            drawable,
            null
        )
    }
}