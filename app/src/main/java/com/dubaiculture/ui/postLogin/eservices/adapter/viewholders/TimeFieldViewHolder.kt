package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import android.view.View
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceTimeFieldItemCellBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.utils.JustTimePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class TimeFieldViewHolder(
    val binding: EserviceTimeFieldItemCellBinding,
    val fieldListener: FieldListener
) : BaseFieldViewHolder(binding.root) {
    private lateinit var fieldValueItem: GetFieldValueItem
    val materialTimePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .build()

    fun onDropDownClicked(view: View) {
        JustTimePicker(view.context, object : JustTimePicker.TimePickerInterface {
            override fun onTimeSelected(calendar: Calendar) {
                val timeParse =
                    SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy").parse(calendar.time.toString())
                val time = SimpleDateFormat("hh:mm aa").format(timeParse)
                binding.time.text = time.toString()
                fieldListener.timeValue(fieldValueItem.copy(selectedValue = time.toString()))


            }
        }).showPicker()
    }

    override fun bind(fieldValue: GetFieldValueItem) {
        fieldValueItem = fieldValue
        binding.fieldClass = this
    }
}