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
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.utils.ProgressDialog
import com.app.dubaiculture.utils.event.EventUtilFunctions
import com.app.dubaiculture.utils.event.EventUtilFunctions.showAlert
import com.app.dubaiculture.utils.event.UiEvent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*


abstract class BaseBottomSheetFragment<DB : ViewDataBinding> : BottomSheetDialogFragment() {
    protected lateinit var application: ApplicationEntry
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity

    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    protected var customProgressDialog: ProgressDialog? = null


    // data binding
    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding


    protected var isBusRegistered: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = activity.application as ApplicationEntry
        groupAdapter = GroupAdapter()
        customProgressDialog = ProgressDialog(activity)

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        application = activity.application as ApplicationEntry
        bus = application.bus
        bus.register(this)
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.apply { lifecycleOwner = viewLifecycleOwner }

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
    fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        baseViewModel.uiEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()
                ?.let { event ->
                    when (event) {
                        is UiEvent.ShowAlert -> {
                            showAlert(event.message)

                        }
                        is UiEvent.ShowToast -> {
                            EventUtilFunctions.showToast(event.message,activity)
                        }
                        is UiEvent.ShowLoader -> {
                            EventUtilFunctions.showLoader(event.show, customProgressDialog)
                        }
                        is UiEvent.ShowSnackbar -> {
                            EventUtilFunctions.showSnackbar(
                                requireView(),
                                event.message,
                                event.action
                            )
                        }
                        is UiEvent.NavigateByDirections -> {
                            navigateByDirections(event.navDirections)
                        }
                        is UiEvent.NavigateByAction -> {
                            navigateByAction(event.actionId, event.bundle)
                        }
                    }
                }
        })
        baseViewModel.userLiveData.observe(viewLifecycleOwner) {
            application.auth.user = it
            application.auth.isGuest = false
        }
    }

    fun navigateByDirections(navDirections: NavDirections) {
        EventUtilFunctions.navigateByDirections(this, navDirections)

    }

    fun navigateByAction(actionId: Int, bundle: Bundle? = null) {
        EventUtilFunctions.navigateByAction(this, actionId, bundle)

    }

    protected fun back() {
        findNavController().popBackStack()
    }

    protected fun navigate(@IdRes resId: Int, bundle: Bundle? = null) {
        findNavController().navigate(resId, bundle)
    }

    fun setLanguage(locale: Locale) {
        (activity as BaseActivity).setLanguage(locale)
    }

    fun getCurrentLanguage(): Locale {
        return (activity as BaseActivity).getCurrentLanguage()
    }
    fun showAlert(message: String) {
        EventUtilFunctions.showAlert(message, activity)
    }

    fun isArabic() = getCurrentLanguage() != Locale.ENGLISH
}