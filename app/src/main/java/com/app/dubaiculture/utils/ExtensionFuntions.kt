package com.app.dubaiculture.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.ui.preLogin.login.LoginFragment
import com.google.android.material.snackbar.Snackbar
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException

fun requestBodyToString(request: RequestBody?): String? {
    return try {
        val buffer = Buffer()
        if (request != null) request.writeTo(buffer) else return ""
        buffer.readUtf8()
    } catch (e: IOException) {
        "IO Exception"
    }

}


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
            it()
        }
    }
    snackBar.show()
}

fun Fragment.handleApiError(
    failure: Result.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetWorkError -> requireView().snackbar(
            "Please Check Your Internet Connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment){
                requireView().snackbar("You have entered incorrect email or password")
            }
        }
    }
}




