package com.app.dubaiculture.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.ui.preLogin.login.LoginFragment
import com.google.android.material.snackbar.Snackbar
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun decorateRecyclerView(
    context: Context,
    recyclerView: RecyclerView,
    layoutManager: LinearLayoutManager
) {
    val dividerItemDecoration = DividerItemDecoration(
        context,
        layoutManager.orientation
    )
    recyclerView.addItemDecoration(dividerItemDecoration)
}


fun requestBodyToString(request: RequestBody?): String? {
    return try {
        val buffer = Buffer()
        if (request != null) request.writeTo(buffer) else return ""
        buffer.readUtf8()
    } catch (e: IOException) {
        "IO Exception"
    }

}


internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(
    context.getColorCompat(
        color
    )
)

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startNewActivityWithPre(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    }
}

fun <A : Activity> Activity.killSessionAndStartNewActivity(activity: Class<A>,clearHistory:Boolean=false) {
    Intent(this, activity).also {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        if (clearHistory){
            it.flags= Intent.FLAG_ACTIVITY_NO_HISTORY
        }
        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        finish()

        startActivity(it)

    }
}

fun <A : Activity> Fragment.startNewActivity(activity: Class<A>) {
    Intent(getActivity(), activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {

            snackBar.show()
            it()
        }
    }

}


fun Fragment.handleApiError(
    failure: Result.Failure,
    baseViewModel: BaseViewModel,
    retry: (() -> Unit)? = null,
//        error: Result.Error?=null,
) {
    baseViewModel.showLoader(false)
    when {
        failure.isNetWorkError -> baseViewModel.showToast(
            "Please Check Your Internet Connection"
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                baseViewModel.showToast("You have entered incorrect email or password")
            } else {

                baseViewModel.showToast("Server Error.")
            }

        }
        failure.errorCode == 500 -> {
            baseViewModel.showToast("Internal Server Error")

        }
        else -> {

            val error = failure.errorBody?.string().toString()
            baseViewModel.showToast("Try to restart the app.")

            startNewActivity(PreLoginActivity::class.java)
        }
    }


}

fun <T : Any> ObservableField<T>.getNonNull(): T = get()!!



fun <R> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<R>(key)

fun Fragment.setNavigationResult(key: String, data: Any?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)

}

fun dateFormat(inputDate: String?): String {
//        2021-03-31T17:19:00 server date format
    var parsed: Date? = null
    var outputDate = ""
    val df_input =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val df_output =
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    try {
        parsed = df_input.parse(inputDate)
        outputDate = df_output.format(parsed)
    } catch (e: ParseException) {
    }
    return outputDate
}


fun dateFormatVisitedPlace(inputDate: String?, formatter: String): String {
//        2021-03-31T17:19:00 server date format
    var parsed: Date? = null
    var outputDate = ""
    val df_input =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val df_output =
        SimpleDateFormat(formatter, Locale.ENGLISH)
    try {
        parsed = df_input.parse(inputDate)
        outputDate = df_output.format(parsed)
    } catch (e: ParseException) {
    }
    return outputDate
}

fun dateFormatEn(inputDate: String?): String {
//        2021-03-31T17:19:00 server date format
    var parsed: Date? = null
    var outputDate = ""
    val df_input =
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val df_output =
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    try {
        parsed = df_input.parse(inputDate)
        outputDate = df_output.format(parsed)
    } catch (e: ParseException) {
    }
    return outputDate
}

fun getTimeSpan(registerDate: String?): Boolean {
    return try {
        val registerEventDate = getDateObj(registerDate ?: "")
        val currentTime = getDateObj(gettime().toString())
        currentTime >= registerEventDate
    } catch (e: ParseException) {
        e.printStackTrace()
        false
    }
}

fun dayOfWeek(inputDate: String?, format: String): String {
//        2021-03-31T17:19:00 server date format
    var parsed: Date? = null
    var outputDate = ""
    val df_input =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val df_output =
        SimpleDateFormat(format)
    try {
        parsed = df_input.parse(inputDate)
        outputDate = df_output.format(parsed)
    } catch (e: ParseException) {
    }
    return outputDate
}

fun getDateObj(dateString: String): Date {
    var date: Date? = null
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    try {
        date = format.parse(dateString)
        System.out.println(date)
        return date

    } catch (e: ParseException) {
        e.printStackTrace()
        return Date()
    }
}

fun Date.toString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}


fun gettime(): String? {
    val c = Calendar.getInstance()
    System.out.println("Current time => " + c.time)
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    return df.format(c.time)
}