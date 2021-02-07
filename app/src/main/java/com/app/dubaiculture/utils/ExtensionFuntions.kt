package com.app.dubaiculture.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.ui.base.BaseViewModel
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

            snackBar.show()
            it()
        }
    }

}

fun Fragment.handleApiError(
    failure: Result.Failure,
    baseViewModel: BaseViewModel,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetWorkError ->  baseViewModel.showSnackbar(
            "Please Check Your Internet Connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment){
                baseViewModel.showSnackbar("You have entered incorrect email or password")
            }else{

                baseViewModel.showSnackbar("Server Error.")
            }

        }
        else ->{

            val error= failure.errorBody?.string().toString()
            baseViewModel.showSnackbar(error)

        }
    }
}




