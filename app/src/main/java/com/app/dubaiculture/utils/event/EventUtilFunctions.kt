package com.app.dubaiculture.utils.event

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.utils.ProgressDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter

object EventUtilFunctions {
    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

    fun showAlert(message: String, context: Context) {
        val resources = context.resources
        MaterialAlertDialogBuilder(context)
            .setTitle("Alert")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
            }
            .show()
    }

    fun showErrorDialog(message:String, title :String?="Alert", @ColorRes colorBg :Int? =null, context: Activity){
        Alerter.create(context)
            .setTitle(title?:"Alert")
            .setText(message)
            .setIcon(R.drawable.error_dialog)
            .setBackgroundColorRes(colorBg?:R.color.red_600)
            .setDuration(2000)
            .setIconColorFilter(0) // Optional - Removes white tint
         //   .setIconSize(R.dimen.custom_icon_size) // Optional - default is 38dp
            .show()
    }

    fun showLoader(show: Boolean, customProgressDialog: ProgressDialog?) {
        if (show) { customProgressDialog?.apply { if (!isShowing){ show() } } }
        else { customProgressDialog?.apply { dismiss() } }
    }
    fun navigateByDirections(fragment: Fragment, navDirections: NavDirections) {
        fragment.findNavController().navigate(navDirections)
    }
    fun navigateByAction(fragment: Fragment, actionId: Int, bundle: Bundle?) {
        fragment.findNavController().navigate(actionId, bundle)
    }
    fun showSnackbar(view: View, message: String, action: (() -> Unit)? = null) {

        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackbar.setAction("Retry") {
                it()
            }

        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            snackbar.setTextColor(view.context.getColor(R.color.colorPrimary))
            snackbar.setActionTextColor(view.context.getColor(R.color.colorPrimary))
        }else{
            snackbar.setTextColor(view.context.resources.getColor(R.color.colorPrimary))
            snackbar.setActionTextColor(view.context.resources.getColor(R.color.colorPrimary))
        }

        snackbar.show()
    }
}

