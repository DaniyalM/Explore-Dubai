package com.app.dubaiculture.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.squareup.otto.Bus

abstract class BaseDialogFragment<DB : ViewDataBinding> : DialogFragment() {
    protected lateinit var application: ApplicationEntry
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity
    private var isBusRegistered: Boolean = false
    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)
    }

    protected abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = getFragmentBinding(inflater, container)
        return dataBinding.root
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



}