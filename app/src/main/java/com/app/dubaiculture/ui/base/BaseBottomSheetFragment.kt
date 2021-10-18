package com.app.dubaiculture.ui.base

import ae.sdg.libraryuaepass.UAEPassController
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.utils.ProgressDialog
import com.app.dubaiculture.utils.event.EventUtilFunctions
import com.app.dubaiculture.utils.event.UiEvent
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
//        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        application = activity.application as ApplicationEntry
        bus = application.bus
        bus.register(this)
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
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
                                EventUtilFunctions.showToast(event.message, activity)
                            }
                            is UiEvent.ShowLoader -> {
                                showLoader(event.show)
//                                EventUtilFunctions.showLoader(event.show, customProgressDialog)
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
                            is UiEvent.ShowErrorDialog -> {
                                EventUtilFunctions.showErrorDialog(event.message,
                                        colorBg = event.colorBg,
                                        context = activity)
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

    fun showToast(message: String) {
        EventUtilFunctions.showToast(message, activity)
    }
    fun showLoader(show: Boolean) {
        EventUtilFunctions.showLoader(show, customProgressDialog)
    }

    fun isArabic() = getCurrentLanguage() != Locale.ENGLISH
    fun showErrorDialog(message: String) {
        EventUtilFunctions.showErrorDialog(message, context = activity)
    }
    fun backArrowRTL(img: ImageView) {
        when {
            isArabic() -> {
                img.rotation = -90f
            }
            else -> {
                img.rotation = 90f
            }
        }
    }

    fun handleIntent(intent: Intent?) {
        if (intent != null && intent.data != null) {
            if (BuildConfig.URI_SCHEME1.equals(intent.data!!.scheme)) {
                UAEPassController.resume(intent.dataString!!)
            }
        }
    }


}