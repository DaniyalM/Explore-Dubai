package com.dubaiculture.utils.event

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dubaiculture.R
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.neomads.ui.components.customDialog.CustomDialog
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.ProgressDialog
import com.dubaiculture.utils.safeNavigateFromNavController
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter

object EventUtilFunctions {
    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun showSnackBar(view: View, message: String, action: (() -> Unit)? = null) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackBar.setAction("Retry") {
                it()
            }
        }
        snackBar.show()
    }

    fun showAlert(
        message: String,
        context: Context,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String? = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionPositive: (() -> Unit)? = null,
        isInternet:Boolean=false,
        application: ApplicationEntry?=null

    ) {
        val customDialog = CustomDialog(
            context,
            R.style.CustomDialogStyle,
            message = message,
            title = title,
            textPositive = textPositive,
            textNegative = textNegative,
            actionPositive = actionPositive,
            isInternet=isInternet,
            application=application
        )
        customDialog.setCancelable(false)
        customDialog.show()
    }

    fun showErrorDialog(
        message: String,
        title: String? = "Alert",
        @ColorRes colorBg: Int? = null,
        context: Activity,
    ) {
        Alerter.create(context)
            .setTitle(title ?: "Alert")
            .setText(message)
            .setIcon(R.drawable.error_dialog)
            .setBackgroundColorRes(colorBg ?: R.color.red_600)
            .setDuration(2000)
            .setIconColorFilter(0) // Optional - Removes white tint
            //   .setIconSize(R.dimen.custom_icon_size) // Optional - default is 38dp
            .show()
    }


    fun showLoader(show: Boolean, customProgressDialog: ProgressDialog?) {
        if (show) {
            if (customProgressDialog!=null && !customProgressDialog.isShowing){
                customProgressDialog.show()
            }
        } else {
            if (customProgressDialog!=null && customProgressDialog.isShowing){
                customProgressDialog.dismiss()
            }
//            customProgressDialog?.apply {
//                if (isShowing) {
//                    dismiss()
//                }
//                dismiss()
//
//            }
        }
    }

    fun navigateByDirections(fragment: Fragment, navDirections: NavDirections) {
        fragment.safeNavigateFromNavController(navDirections)

//        fragment.findNavController().navigate(navDirections)
    }

    fun navigateByAction(fragment: Fragment, actionId: Int, bundle: Bundle?) {
        fragment.findNavController().navigate(actionId, bundle)
    }
    fun navigateByActionNavOptions(fragment: Fragment, actionId: Int, bundle: Bundle?,navOptions: NavOptions) {
        fragment.findNavController().navigate(actionId, bundle,navOptions)
    }

    fun showSnackbar(view: View, message: String, action: (() -> Unit)? = null) {

        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackbar.setAction("Retry") {
                it.elevation =1000F
            }
        }

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            snackbar.setTextColor(view.context.getColor(R.color.colorPrimary))
//            snackbar.setActionTextColor(view.context.getColor(R.color.colorPrimary))
//        } else {
//            snackbar.setTextColor(view.context.resources.getColor(R.color.colorPrimary))
//            snackbar.setActionTextColor(view.context.resources.getColor(R.color.colorPrimary))
//        }

        snackbar.show()
    }
    fun navigateBack(fragment: Fragment){
        fragment.findNavController().popBackStack()
    }
}

