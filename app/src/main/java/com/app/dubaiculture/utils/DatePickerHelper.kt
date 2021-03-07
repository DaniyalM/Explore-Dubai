package com.app.dubaiculture.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DatePickerHelper(var selectedDate : String?=null , var context:Context ,var iface : DatePickerInterface): DatePickerDialog.OnDateSetListener {
    private val selectCurrentDate = Calendar.getInstance()
    fun showPicker() {
        when {
            !selectedDate.isNullOrEmpty() -> {
                val datePickerDialog = DatePickerDialog(
                    context, this,
                    YYYY_MM_DD(selectedDate.toString(), "yyyy"),
                    YYYY_MM_DD(selectedDate.toString(), "MM").minus(1),
                    YYYY_MM_DD(selectedDate.toString(), "dd")
                )
                datePickerDialog.show()
            }
            else -> {
                val datePickerDialog = DatePickerDialog(
                    context, this,
                    selectCurrentDate.get(Calendar.YEAR),
                    selectCurrentDate.get(Calendar.MONTH),
                    selectCurrentDate.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }
        }

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
  private  fun YYYY_MM_DD(dateString : String, format :String) : Int {
        return try {
            val myDateFormat = SimpleDateFormat("dd/MM/yy")
            val reqOutput = SimpleDateFormat(format, Locale.getDefault())
            val d = myDateFormat.parse(dateString)
            val formatteddate = reqOutput.format(d)
            Timber.d("formattedDate" + formatteddate.toString())
            formatteddate.toInt()
        }catch (e: Exception){
            0
        }
    }
}