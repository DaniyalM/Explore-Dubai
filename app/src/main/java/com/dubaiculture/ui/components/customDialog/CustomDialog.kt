package com.dubaiculture.neomads.ui.components.customDialog

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.dubaiculture.databinding.CustomAlertDialogBinding
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.utils.AppConfigUtils.isInternetAvailable
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show

class CustomDialog(
    context: Context, themeResId: Int,
    val message: String,
    val title: String = Constants.Alert.DEFAULT_TITLE,
    val textPositive: String? = Constants.Alert.DEFAULT_TEXT_POSITIVE,
    val textNegative: String? = null,
    val actionPositive: (() -> Unit)? = null,
    val isInternet: Boolean = false,
    val application: ApplicationEntry?=null
) : Dialog(context, themeResId) {

    lateinit var binding: CustomAlertDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        if (isInternet) {
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            binding.normalAlert.hide()
            binding.internetView.show()
            binding.customButton.setOnClickListener {
                if (isInternetAvailable(context)){
                    dismiss()
                }
            }
        } else {
            binding.normalAlert.show()
            binding.internetView.hide()
        }
        setupView(message, title, textPositive, textNegative, actionPositive)
    }

    private fun setupView(
        message: String,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String? = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionPositive: (() -> Unit)? = null
    ) {
        binding.title.text = title
        binding.description.text = message
        binding.btnYes.text = textPositive
        textNegative?.apply {
            binding.btnNo.visibility = View.VISIBLE
            binding.btnNo.text = this
        }
        binding.btnYes.setOnClickListener {
            if (actionPositive != null) {
                actionPositive()
                dismiss()
            } else dismiss()
        }

        binding.btnNo.setOnClickListener { dismiss() }

    }
}