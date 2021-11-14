package com.dubaiculture.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.dubaiculture.R
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.NetworkLiveData
import com.dubaiculture.utils.ProgressDialog
import com.dubaiculture.utils.event.EventUtilFunctions
import com.dubaiculture.utils.event.UiEvent
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*

abstract class BaseDialogFragment<DB : ViewDataBinding> : DialogFragment() {
    protected lateinit var application: ApplicationEntry
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity
    private var isBusRegistered: Boolean = false
    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding
    protected var customProgressDialog: ProgressDialog? = null
    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)
    }

    protected abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dataBinding = getFragmentBinding(inflater, container)
        return dataBinding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        application = activity.application as ApplicationEntry
        bus = application.bus
        bus.register(this)
        isBusRegistered = true
        customProgressDialog = ProgressDialog(activity)
        groupAdapter = GroupAdapter()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }
    }

    fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        baseViewModel.uiEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()
                ?.let { event ->
                    when (event) {
                        is UiEvent.ShowAlert -> {
                            showAlert(event.message)

                        }

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
                        is UiEvent.ShowErrorDialog -> {
                            EventUtilFunctions.showErrorDialog(event.message,
                                colorBg = event.colorBg,
                                context = activity)
                        }
                    }
                }
        })

        NetworkLiveData.observe(viewLifecycleOwner) {
            if (!it) {
                baseViewModel.showLoader(false)
                baseViewModel.showToast("Network Connection Lost..")
            }
        }

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
        hideKeyboard(requireActivity())
        activity.onBackPressed()
    }

    fun navigate(@IdRes resId: Int, bundle: Bundle? = null) {
        findNavController().navigate(resId, bundle)
    }


    public fun setLanguage(locale: Locale) {
        (activity as BaseActivity).setLanguage(locale)
    }

    public fun getCurrentLanguage(): Locale {
        return (activity as BaseActivity).getCurrentLanguage()
    }

    fun showAlert(
        message: String,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionNegative: (() -> Unit)? = null,
        actionPositive: (() -> Unit)? = null,
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

    fun showErrorDialog(message: String) {
        EventUtilFunctions.showErrorDialog(message, context = activity)
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

    fun arrowRTL(img: ImageView) {
        when {
            isArabic() -> {
                img.rotation = -180f
            }
        }
    }

    fun cardViewRTL(materialCardView: MaterialCardView) {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        if (isArabic()) {
            materialCardView.shapeAppearanceModel =
                materialCardView.shapeAppearanceModel
                    .toBuilder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .setTopRightCornerSize(radius)
                    .build()
        } else {
            materialCardView.shapeAppearanceModel =
                materialCardView.shapeAppearanceModel
                    .toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                    .setBottomRightCornerSize(radius)
                    .build()
        }
    }


    open fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    open fun adjustFontScale(configuration: Configuration) {
        if (configuration.fontScale > 1.30) {
            configuration.fontScale = 1.30f
            val metrics = resources.displayMetrics
            val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager?
            wm!!.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            this.getResources().updateConfiguration(configuration, metrics)
        }
    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(getResources().getConfiguration());
    }

    protected fun getDrawableFromId(resId: Int?): Drawable? {
        resId?.let {
            return if (it == 0) null
            else ResourcesCompat.getDrawable(resources, it, null)
        }
        return null
    }
    fun bgRTL(img: ImageView) {
        when {
            isArabic() -> {
                img.scaleX = -1f
            }
            else -> {
                img.scaleX = 1f
            }
        }
    }



//    fun openEmailbox(email: String) {
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = "plain/text"
//        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
//        requireActivity().startActivity(Intent.createChooser(intent, ""))
//    }
//
//    fun openDiallerBox(number: String?=null) {
//        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
//        requireActivity().startActivity(intent)
//    }

//    fun favouriteClick(
//        checkbox: CheckBox,
//        isFav: Boolean,
//        nav: Int,
//        itemId: String,
//        baseViewModel: BaseViewModel,
//        type: Int = 2,
//    ) {
//        checkBox = checkbox
//        if (application.auth.isGuest) {
//            navigate(nav)
//        } else {
//            if (!isFav) {
//                application.auth.user?.let {
//                    baseViewModel.addToFavourites(AddToFavouriteRequest(
//                        userId = application.auth.user?.userId,
//                        itemId = itemId,
//                        type = type
//                    )
//                    )
//                }
//            } else {
//                application.auth.user?.let {
//                    baseViewModel.addToFavourites(AddToFavouriteRequest(
//                        userId = application.auth.user?.userId,
//                        itemId = itemId,
//                        type = type
//                    )
//                    )
//                }
//            }
//
//        }
}




