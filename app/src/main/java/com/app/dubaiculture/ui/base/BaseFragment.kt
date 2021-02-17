package com.app.dubaiculture.ui.base

import android.app.Activity
import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.app.dubaiculture.R
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.utils.ProgressDialog
import com.app.dubaiculture.utils.event.EventUtilFunctions
import com.app.dubaiculture.utils.event.EventUtilFunctions.showAlert
import com.app.dubaiculture.utils.event.UiEvent
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    protected lateinit var application: ApplicationEntry
    protected var isBusRegistered: Boolean = false
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity
    protected var customProgressDialog: ProgressDialog? = null
    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>


    // data binding
    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = activity.application as ApplicationEntry
        bus = application.bus
        bus.register(this)
        isBusRegistered = true
        customProgressDialog = ProgressDialog(activity)
        groupAdapter = GroupAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = getFragmentBinding(inflater, container)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModel is set as Binding Variable
        dataBinding.apply { lifecycleOwner = viewLifecycleOwner }
    }

    protected abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): DB


    override fun onDestroy() {
        super.onDestroy()
        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }
        groupAdapter.clear()

    }

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

    public fun setLanguage(locale: Locale) {
        (activity as BaseActivity).setLanguage(locale)
    }

    public fun getCurrentLanguage(): Locale {
        return (activity as BaseActivity).getCurrentLanguage()
    }

    fun showAlert(message: String) {
        EventUtilFunctions.showAlert(message, activity)
    }

    fun isArabic() = getCurrentLanguage() != Locale.ENGLISH

    fun lottieAnimationRTL(img: LottieAnimationView) {
        when {
            isArabic() -> {
                img.scaleX = -1f
            }
            else -> {
                img.scale = 1f
            }
        }
    }
    fun backArrowRTL(img:ImageView){
        when {
            isArabic() -> {
            img.rotation = -90f
        }
            else -> {
                img.rotation = 90f
        }
        }
    }
}