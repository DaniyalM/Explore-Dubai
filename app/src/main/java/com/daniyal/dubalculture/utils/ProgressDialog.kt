package com.daniyal.dubalculture.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.daniyal.dubalculture.R

class ProgressDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.progress_dialog_layout)
        getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setCancelable(false)
        setCanceledOnTouchOutside(false)
//        val rotate = RotateAnimation(
//            0f, 360f,
//            Animation.RELATIVE_TO_SELF, 0.5f,
//            Animation.RELATIVE_TO_SELF, 0.5f
//        )
//        rotate.duration = 1000
//        rotate.repeatCount = Animation.INFINITE
//        iv_logo.startAnimation(rotate)

    }
}