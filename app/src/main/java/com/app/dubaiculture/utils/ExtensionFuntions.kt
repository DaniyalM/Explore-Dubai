package com.app.dubaiculture.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.ui.base.BaseViewModel
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
    val dividerItemDecoration = DividerItemDecoration(context,
        layoutManager.getOrientation())
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


internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(
    color))
internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.killSessionAndStartNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
        finish()
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
        else -> {

            val error = failure.errorBody?.string().toString()
            baseViewModel.showToast(error)

        }
    }
}
fun <T : Any> ObservableField<T>.getNonNull(): T = get()!!

//@BindingAdapter("android:imageViewUrl")
//fun loadImageView(view: ImageView, url: String?) {
//    url?.let {
//        Glide.with(view.context)
//            .load(BuildConfig.BASE_URL + it)
////                .apply(
////                    RequestOptions()
//////                        .placeholder(R.drawable.logo)
//////                        .error(R.drawable.logo))
//            .into(view)
//    }
//}
//
//@BindingAdapter("android:svgUrl")
//fun loadSvgToImageView(view: ImageView, url: String?) {
//    url?.let {
//        Glide.with(view.context)
//            .load(BuildConfig.BASE_URL + it)
//            .into(view)
//    }
//
//}

fun <R> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<R>(key)

fun Fragment.setNavigationResult(key: String, data: Any?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)

}
    fun dateFormat(inputDate: String?): String {
//        2021-03-31T17:19:00 server date format
        var parsed: Date? = null
        var outputDate = "- - - -"
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

fun getDateObj(dateString: String): Date {
    var date : Date? =null
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    try {
         date = format.parse(dateString)
        System.out.println(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date!!
}

    fun Date.toString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }


fun _timeToDateObj(time24: String): Date {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(time24)
}