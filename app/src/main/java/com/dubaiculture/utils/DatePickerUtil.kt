package com.dubaiculture.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.dubaiculture.R
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

//https://stackoverflow.com/questions/30239627/how-to-change-the-style-of-a-datepicker-in-android
class DatePickerUtil(
    var selectedDate: String? = null,
    var selectedDateFormat: String? = null,
    var context: Context,
    var iface: DatePickerInterface,
    var showFutureDates: Boolean = true,
    var showPastDates: Boolean = true
) : DatePickerDialog.OnDateSetListener {
    private val selectCurrentDate = Calendar.getInstance()
    fun showPicker() {
        val year: Int
        val month: Int
        val date: Int
        val datePickerDialog: DatePickerDialog
        when {
            !selectedDate.isNullOrEmpty() && !selectedDateFormat.isNullOrEmpty() -> {
                year = getDateInfo(selectedDate!!, selectedDateFormat!!, "yyyy")
                month = getDateInfo(selectedDate!!, selectedDateFormat!!, "MM").minus(1)
                date = getDateInfo(selectedDate!!, selectedDateFormat!!, "dd")
            }
            else -> {
                year = selectCurrentDate.get(Calendar.YEAR)
                month = selectCurrentDate.get(Calendar.MONTH)
                date = selectCurrentDate.get(Calendar.DAY_OF_MONTH)
            }
        }
        datePickerDialog = DatePickerDialog(
            context,
            R.style.my_dialog_theme,
            this,
            year,
            month,
            date
        )
        if (!showFutureDates)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        if (!showPastDates) {
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        }
        datePickerDialog.show()

    }

    interface DatePickerInterface {
        fun onDateSelected(calendar: Calendar)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selectCurrentDate.set(Calendar.YEAR, year)
        selectCurrentDate.set(Calendar.MONTH, month)
        selectCurrentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        iface.onDateSelected(selectCurrentDate)
    }

    private fun getDateInfo(dateString: String, inputFormat: String, outputFormat: String): Int {
        return try {
            val myDateFormat = SimpleDateFormat(inputFormat)
            val reqOutput = SimpleDateFormat(outputFormat, Locale.getDefault())
            val d = myDateFormat.parse(dateString)
            val formattedDate = reqOutput.format(d)
            Timber.d("formattedDate%s", formattedDate.toString())
            formattedDate.toInt()
        } catch (e: Exception) {
            0
        }
    }
}