package com.dubaiculture.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.dubaiculture.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class DatePickerHelper(var selectedDate : String?=null , var context:Context ,var iface : DatePickerInterface,var minDate: Long? = Date().time,var maxDate: Long? = null,var fromDate : Boolean?=true): DatePickerDialog.OnDateSetListener {


    private val selectCurrentDate = Calendar.getInstance()
    fun showPicker() {
        val datePickerDialog = DatePickerDialog(
            context,
            R.style.my_dialog_theme,
            this,
            selectCurrentDate.get(Calendar.YEAR),
            selectCurrentDate.get(Calendar.MONTH),
            selectCurrentDate.get(Calendar.DAY_OF_MONTH)
        )

        if(fromDate == true) {
            maxDate?.let {
                // prevent date picker from being selected future dates
                datePickerDialog.datePicker.maxDate = it
            } ?: run {
                // remove all min/max dates from date picker
                minDate?.let {
                    // prevent date picker from being selected previous dates
                    datePickerDialog.datePicker.minDate = it
                }
            }
        }

        datePickerDialog.show()

//        when {
//            !selectedDate.isNullOrEmpty() -> {
//                val datePickerDialog = DatePickerDialog(
//                    context, this,
//                    YYYY_MM_DD(selectedDate.toString(), "yyyy"),
//                    YYYY_MM_DD(selectedDate.toString(), "MM").minus(1),
//                    YYYY_MM_DD(selectedDate.toString(), "dd")
//                )
//                datePickerDialog.show()
//            }
//            else -> {
//                val datePickerDialog = DatePickerDialog(
//                    context, this,
//                    selectCurrentDate.get(Calendar.YEAR),
//                    selectCurrentDate.get(Calendar.MONTH),
//                    selectCurrentDate.get(Calendar.DAY_OF_MONTH)
//                )
//                datePickerDialog.show()
//            }
//        }

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
     fun YYYY_MM_DD(dateString : String, format :String) : Int {
        return try {
            val myDateFormat = SimpleDateFormat("dd/MM/yy",Locale.getDefault())
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