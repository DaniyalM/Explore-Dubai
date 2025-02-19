package com.dubaiculture.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.*
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.ui.preLogin.login.LoginFragment
import com.dubaiculture.utils.Constants.HTTP_RESPONSE.HTTP_400
import com.dubaiculture.utils.Constants.HTTP_RESPONSE.HTTP_401
import com.dubaiculture.utils.Constants.HTTP_RESPONSE.HTTP_500
import com.google.android.material.snackbar.Snackbar
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.HttpException
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.pluralize(count: Int): String? {
    return this.pluralize(count, null)
}

fun String.pluralize(count: Int, plural: String?): String? {
    return if (count > 1) {
        plural ?: this + 's'
    } else {
        this
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun Activity.openPdf(filename: String?) {

    val fn = filename?.substring(filename.lastIndexOf("/") + 1)
    val file = File(filesDir, fn!!)

    if (file.exists()) {
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                FileProvider.getUriForFile(
                    this@openPdf,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    file
                ), "application/pdf"
            )
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                startActivity(this)
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    } else {
        Timber.e("file doesn't exist")
    }
}

fun Exception.triggerException() {
    when (this) {
        is HttpException -> {
            when (code()) {
                200 -> {
                }
                300 -> {
                }
                400 -> {
                }
            }
        }
        is IllegalArgumentException -> {
            Timber.e(message)
        }
        else -> {
            Timber.e(message)
        }
    }
}

fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>,
    callback: (showPlaceHolder: Boolean) -> Unit
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
        if (loadStates.source.refresh is LoadState.NotLoading &&
            loadStates.append.endOfPaginationReached &&
            itemCount < 1
        ) {
            callback(true)
        } else if (loadStates.source.refresh is LoadState.Error &&
            itemCount < 1
        ) {
            callback(true)
        } else if (itemCount < 1) {
            callback(true)
        } else {
            callback(false)
        }
    }

    return ConcatAdapter(header, this, footer)
}


//fun <T : Any, V : RecyclerView.ViewHolder> ListAdapter<T, V>.withLoadStateAdapters(
//    header: LoadStateAdapter<*>,
//    footer: LoadStateAdapter<*>,
//    callback: (showPlaceHolder: Boolean) -> Unit
//): ConcatAdapter {
//    addLoadStateListener { loadStates ->
//        header.loadState = loadStates.refresh
//        footer.loadState = loadStates.append
//        if (loadStates.source.refresh is LoadState.NotLoading &&
//            loadStates.append.endOfPaginationReached &&
//            itemCount < 1
//        ) {
//            callback(true)
//        } else if (loadStates.source.refresh is LoadState.Error &&
//            itemCount < 1
//        ) {
//            callback(true)
//        } else {
//            callback(false)
//        }
//    }
//
//    return ConcatAdapter(header, this, footer)
//}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

fun Fragment.safeNavigateFromNavController(directions: NavDirections) {
    val navController = findNavController()
    when (navController.currentDestination) {
        is FragmentNavigator.Destination -> {
            val destination = navController.currentDestination as FragmentNavigator.Destination
            //current visible fragment == fragment that is firing navigation
            if (javaClass.name == destination.className) {
                navController.navigate(directions)
            } else {
                Timber.e("Invalid navigation detected")
            }
        }
        is DialogFragmentNavigator.Destination -> {
            val destination =
                navController.currentDestination as DialogFragmentNavigator.Destination
            //current visible fragment == fragment that is firing navigation
            if (javaClass.name == destination.className) {
                navController.navigate(directions)
            } else {
                Timber.e("Invalid navigation detected")
            }
        }
    }

}

fun Activity.enableLocationFromSettings() {
    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}


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

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

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
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    }
}

fun <A : Activity> Activity.killSessionAndStartNewActivity(activity: Class<A>) {

    Intent(this, activity).also {
        finish()
        startActivity(it)
    }
}

//fun <A : Activity> Activity.killSessionAndStartNewActivityUAE(activity: Class<A>) {
//
//    Intent(this, activity).also {
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//        finish()
//        startActivity(it)
//    }
//}

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
        failure.errorCode == HTTP_401 -> {
            if (this is LoginFragment) {
                baseViewModel.showToast("You have entered incorrect email or password")
            } else {

                baseViewModel.showToast("Server Error.")
            }

        }
        failure.errorCode == HTTP_500 -> {
            baseViewModel.showToast("Internal Server Error")
        }
        failure.errorCode == HTTP_400 -> {
            baseViewModel.showToast("Session Timeout From Server")
            retry?.invoke()
        }
        else -> {
//            val error = failure.errorBody?.string().toString()
            baseViewModel.showToast("Session Timeout From Server")
            initiateLogout()
        }
    }


}

fun Fragment.initiateLogout() {
    val broadcastIntent = Intent()
    broadcastIntent.action = "com.package.ACTION_LOGOUT"
    activity?.sendBroadcast(broadcastIntent)
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

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val dateFormatter = SimpleDateFormat(format, locale)
    return dateFormatter.format(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.show(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun gettime(): String? {
    val c = Calendar.getInstance()
    System.out.println("Current time => " + c.time)
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    return df.format(c.time)
}

fun TextView.setColouredSpan(word: String, color: Int) {
    val spannableString = SpannableString(text)
    val start = text.indexOf(word)
    val end = text.indexOf(word) + word.length
    try {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableString
    } catch (e: IndexOutOfBoundsException) {
        Timber.e("'$word' was not not found in TextView text")
    }
}