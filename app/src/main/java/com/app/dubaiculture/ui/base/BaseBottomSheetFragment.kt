package com.app.dubaiculture.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.otto.Bus

class BaseBottomSheetFragment : BottomSheetDialogFragment() {
    protected lateinit var application: ApplicationEntry
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity



    protected var isBusRegistered: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        application = activity.application as ApplicationEntry
        bus = application.bus
        bus.register(this)
        isBusRegistered = true
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }
    }


    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Objects.requireNonNull<Window>(dialog?.window)
//                .getAttributes().windowAnimations = R.style.MyAnimation_Window
        }
    }
}