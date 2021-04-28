package com.app.dubaiculture.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.preLogin.login.LoginFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rishabhharit.roundedimageview.RoundedImageView
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException

fun decorateRecyclerView(context: Context,recyclerView: RecyclerView, layoutManager: LinearLayoutManager) {
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
fun <T: Any> ObservableField<T>.getNonNull(): T = get()!!

@BindingAdapter("android:imageViewUrl")
fun loadImageView(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(BuildConfig.BASE_URL + it)
//                .apply(
//                    RequestOptions()
////                        .placeholder(R.drawable.logo)
////                        .error(R.drawable.logo))
            .into(view)
    }
}

@BindingAdapter("android:svgUrl")
fun loadSvgToImageView(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(BuildConfig.BASE_URL + it)
            .into(view)
    }

}

fun <R> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<R>(key)

fun Fragment.setNavigationResult(key: String, data: Any?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
}


