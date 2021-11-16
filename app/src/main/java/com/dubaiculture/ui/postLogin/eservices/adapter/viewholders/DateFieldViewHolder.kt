package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import android.view.View
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceDateFieldItemCellBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.utils.DatePickerHelper
import com.dubaiculture.utils.toString
import java.util.*

class DateFieldViewHolder(
    val binding: EserviceDateFieldItemCellBinding,
    val fieldListener: FieldListener
) : BaseFieldViewHolder(binding.root) {
    private lateinit var fieldValueItem: GetFieldValueItem

    fun onDropDownClicked(view: View) {
        DatePickerHelper(
            context = view.context,
            iface = object : DatePickerHelper.DatePickerInterface {
                override fun onDateSelected(calendar: Calendar) {
                    val date: Date = calendar.time
//                    val format = "yyyy-MM-dd"
                    val format = "MM/dd/YYYY"
                    val str = date.toString(format)
                    binding.date.text = str
                    fieldListener.dateValue(fieldValueItem.copy(selectedValue = str))


                }
            }
        ).showPicker()
    }

    override fun bind(fieldValue: GetFieldValueItem) {
        fieldValueItem = fieldValue
        binding.date.text =fieldValue.selectedValue ?: fieldValue.english

        binding.fieldClass = this
    }
}