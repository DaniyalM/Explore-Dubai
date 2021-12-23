package com.dubaiculture.utils

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import java.util.*

class JustTimePicker(
    var context: Context,
    var iface: TimePickerInterface?,
    var selectedTime: Calendar = Calendar.getInstance()
) : TimePickerDialog.OnTimeSetListener {

    fun showPicker() {
        val timePickerDialog = TimePickerDialog(
            context,
            this,
            selectedTime.get(Calendar.HOUR_OF_DAY),
            selectedTime.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()

    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        selectedTime.set(Calendar.HOUR_OF_DAY, hour)
        selectedTime.set(Calendar.MINUTE, min)
        iface?.onTimeSelected(selectedTime)
    }

    interface TimePickerInterface {
        fun onTimeSelected(calendar: Calendar)
    }
}