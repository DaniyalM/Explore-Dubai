package com.dubaiculture.utils

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.dubaiculture.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class JustTimePicker(
    var context: Context,
    var iface: TimePickerInterface?,
    var fragmentManager: FragmentManager,
    var selectedTime: Calendar = Calendar.getInstance()
) {

    fun showPicker() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(selectedTime.get(Calendar.HOUR_OF_DAY))
                .setMinute(selectedTime.get(Calendar.MINUTE))
                .setTitleText(context.resources.getString(R.string.select_time))
                .setTheme(R.style.TimePicker)
                .build()
        picker.show(fragmentManager, "TimePicker")

        picker.addOnPositiveButtonClickListener {
            selectedTime.set(Calendar.HOUR_OF_DAY, picker.hour)
            selectedTime.set(Calendar.MINUTE, picker.minute)
            iface?.onTimeSelected(selectedTime)
        }
    }

    interface TimePickerInterface {
        fun onTimeSelected(calendar: Calendar)
    }
}