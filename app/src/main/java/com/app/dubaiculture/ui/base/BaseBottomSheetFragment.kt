package com.app.dubaiculture.ui.base

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


abstract class BaseBottomSheetFragment<DB : ViewDataBinding> : BottomSheetDialogFragment() {
    protected lateinit var application: ApplicationEntry
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity

    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>



    // data binding
    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding


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
        groupAdapter = GroupAdapter()


        return super.onCreateDialog(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }
        groupAdapter.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = getFragmentBinding(inflater, container)
        return dataBinding.root
    }

    protected abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): DB

}