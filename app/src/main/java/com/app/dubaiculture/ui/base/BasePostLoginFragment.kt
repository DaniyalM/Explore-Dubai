package com.app.dubaiculture.ui.base


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.airbnb.lottie.LottieAnimationView
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.ProgressDialog
import com.app.dubaiculture.utils.event.EventUtilFunctions
import com.app.dubaiculture.utils.event.UiEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*


abstract class BasePostLoginFragment : Fragment() {

    protected lateinit var application: ApplicationEntry
    protected var isBusRegistered: Boolean = false
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity
    protected var customProgressDialog: ProgressDialog? = null
    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    var hasInitializedRootView = false

    fun getCurrentLanguage(): Locale {
        return (activity as BaseActivity).getCurrentLanguage()
    }

    fun isArabic() = getCurrentLanguage() != Locale.ENGLISH

    // data binding
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

    override fun onDestroy() {
        super.onDestroy()
        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }
        groupAdapter.clear()

    }

    fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        baseViewModel.uiEvents.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()
                ?.let { event ->
                    when (event) {
                        is UiEvent.ShowAlert -> {
                            showAlert(
                                message = event.message,
                                title = event.title,
                                textPositive = event.textPositive,
                                textNegative = event.textNegative,
                                actionPositive = event.actionPositive
                            )
                        }
                        is UiEvent.ShowToast -> {
                            showToast(event.message)
                        }
                        is UiEvent.ShowLoader -> {
                            showLoader(event.show)
                        }
                        is UiEvent.ShowSnackbar -> {
                            showSnackBar(
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
                        is UiEvent.ShowBottomSheet -> {
                            event.bottomSheetFragment.show(
                                requireActivity().supportFragmentManager, event.tag
                            )
                        }
                        is UiEvent.NavigateByActionNavOption -> {
                            navigateByActionNavOptions(
                                event.actionId,
                                event.bundle,
                                event.navOptions
                            )
                        }
                    }
                }
        }
    }

    fun showBottomSheet(
        bottomSheetFragment: BottomSheetDialogFragment,
        tag: String? = null
    ) {
        bottomSheetFragment.show(
            requireActivity().supportFragmentManager, tag
        )
    }

    fun showLoader(show: Boolean) {
        EventUtilFunctions.showLoader(show, customProgressDialog)
    }

    fun showToast(message: String) {
        EventUtilFunctions.showToast(message, activity)
    }

    fun showAlert(
        message: String,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionPositive: (() -> Unit)? = null
    ) {
        EventUtilFunctions.showAlert(
            message = message,
            context = activity,
            title = title,
            textPositive = textPositive,
            textNegative = textNegative,
            actionPositive = actionPositive
        )
    }

    fun showSnackBar(message: String, action: (() -> Unit)?) {
        EventUtilFunctions.showSnackBar(
            requireView(),
            message,
            action
        )
    }

    fun navigateByDirections(navDirections: NavDirections) {
        EventUtilFunctions.navigateByDirections(this, navDirections)

    }

    fun navigateByAction(actionId: Int, bundle: Bundle? = null) {
        EventUtilFunctions.navigateByAction(this, actionId, bundle)

    }

    fun navigateByActionNavOptions(actionId: Int, bundle: Bundle? = null, navOptions: NavOptions) {
        EventUtilFunctions.navigateByActionNavOptions(this, actionId, bundle, navOptions)
    }

    open fun back() {
//        findNavController().popBackStack()
        activity.onBackPressed()
//       if (!BACK_STATE_FLAG) {
//           BACK_STATE_FLAG=true
//       }
    }

}