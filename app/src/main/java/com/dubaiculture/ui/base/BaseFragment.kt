package com.dubaiculture.ui.base

import ae.sdg.libraryuaepass.UAEPassController
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.NetworkRequest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ScrollView
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.dubaiculture.BuildConfig
import com.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.ProgressDialog
import com.dubaiculture.utils.event.EventUtilFunctions
import com.dubaiculture.utils.event.EventUtilFunctions.showToast
import com.dubaiculture.utils.event.UiEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.otto.Bus
import java.util.*


abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    protected lateinit var application: ApplicationEntry
    protected var isBusRegistered: Boolean = false
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity



    protected var customProgressDialog: ProgressDialog? = null

    //    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var networkRequest: NetworkRequest
    private var scrollView: ScrollView? = null


    var checkBox: CheckBox? = null


    // data binding
    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding


    private var _view: View? = null
    protected var isPagerFragment = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_view == null || isPagerFragment) {
            dataBinding = getFragmentBinding(inflater, container)
            _view = dataBinding.root
        }


        return _view
    }

    fun setScrollView(sv: ScrollView) {
        scrollView = sv
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollView?.apply {
            outState.putFloat(Constants.NavBundles.SCROLL_VIEW_STATE, y)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getFloat(Constants.NavBundles.SCROLL_VIEW_STATE)?.apply {
            scrollView?.y = this
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)

    }

    override fun onStart() {
        super.onStart()
        adjustFontScale(resources.configuration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        }catch (ex:Exception){
            back()
        }



        application = activity.application as ApplicationEntry
        application.auth.locale = getCurrentLanguage().language
        application.auth.user?.let {
            application.auth.isGuest = false
        }

        bus = application.bus
        bus.register(this)
        isBusRegistered = true
        customProgressDialog = ProgressDialog(activity)

//        groupAdapter = GroupAdapter()

        (activity as BaseActivity).darkModeAccess()
    }

    override fun onPause() {
        super.onPause()
        if (customProgressDialog!!.isShowing) {
            customProgressDialog!!.dismiss()
        }
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
//        groupAdapter.clear()

        if (customProgressDialog != null) {
            customProgressDialog = null
        }

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

    fun showLoader(show: Boolean) {
        EventUtilFunctions.showLoader(show, customProgressDialog)
    }

    fun showToast(message: String) {
        EventUtilFunctions.showToast(message, activity)
    }

    fun navigateByActionNavOptions(actionId: Int, bundle: Bundle? = null, navOptions: NavOptions) {
        EventUtilFunctions.navigateByActionNavOptions(this, actionId, bundle, navOptions)
    }

    fun showSnackBar(message: String, action: (() -> Unit)?) {
        EventUtilFunctions.showSnackBar(
            requireView(),
            message,
            action
        )
    }

    fun showBottomSheet(
        bottomSheetFragment: BottomSheetDialogFragment,
        tag: String? = null
    ) {
        bottomSheetFragment.show(
            requireActivity().supportFragmentManager, tag
        )
    }


    fun navigateByDirections(navDirections: NavDirections) {
        EventUtilFunctions.navigateByDirections(this, navDirections)
    }

    fun navigateByAction(actionId: Int, bundle: Bundle? = null) {
        EventUtilFunctions.navigateByAction(this, actionId, bundle)

    }

    protected fun back() {
        hideKeyboard(activity)
        activity.onBackPressed()
    }

    fun navigateBack() {
        EventUtilFunctions.navigateBack(this)
    }

    fun navigate(@IdRes resId: Int, bundle: Bundle? = null, extras: Navigator.Extras? = null) {
        try {
            if (extras == null) {
                findNavController().navigate(resId, bundle)
            } else {
                findNavController().navigate(resId, bundle, null, navigatorExtras = extras)
            }
        } catch (ex: IllegalArgumentException) {

            Log.e(Companion.TAG, ex.localizedMessage)
        }

    }

    fun setLanguage(locale: Locale) {
        (activity as BaseActivity).setLanguage(locale)
    }

    fun getCurrentLanguage(): Locale {
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

    fun bgEventtRTL(img: ImageView) {
        when {
            isArabic() -> {
                img.scaleX = 1f
            }
            else -> {
                img.scaleX = -1f
            }
        }
    }

    fun bgAboutRTL(img: ImageView) {
        when {
            isArabic() -> {
                img.scaleX = -1f
            }
            else -> {
                img.scaleX = 1f
            }
        }
    }

    fun arrowRTL(img: ImageView, inverted: Boolean = false) {
        when {
            isArabic() -> {
                if (!inverted)
                    img.rotation = -180f
                else
                    img.rotation = 0f


            }
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
            val wm = requireContext().getSystemService(WINDOW_SERVICE) as WindowManager?
            wm!!.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            this.resources.updateConfiguration(configuration, metrics)
        }
    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)
    }

    protected fun getDrawableFromId(resId: Int?): Drawable? {
        resId?.let {
            return if (it == 0) null
            else ResourcesCompat.getDrawable(resources, it, null)
        }
        return null
    }


    fun openEmailbox(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        requireActivity().startActivity(Intent.createChooser(intent, ""))
    }

    fun openWebURL(url: String) {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(BuildConfig.BASE_URL + url)
        )
        startActivity(browserIntent)
    }

    fun openWebWithoutBaseUrl(url: String) {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        startActivity(browserIntent)
    }

    fun openDiallerBox(number: String? = null) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        requireActivity().startActivity(intent)
    }

    fun favouriteClick(
        checkbox: CheckBox,
        isFav: Boolean,
        nav: Int,
        itemId: String,
        baseViewModel: BaseViewModel,
        type: Int = 2,
    ) {
        checkBox = checkbox
        if (application.auth.isGuest) {
            navigate(nav)
        } else {
            if (!isFav) {
                application.auth.user?.let {
                    baseViewModel.addToFavourites(
                        AddToFavouriteRequest(
                            userId = application.auth.user?.userId,
                            itemId = itemId,
                            type = type
                        )
                    )
                }
            } else {
                application.auth.user?.let {
                    baseViewModel.addToFavourites(
                        AddToFavouriteRequest(
                            userId = application.auth.user?.userId,
                            itemId = itemId,
                            type = type
                        )
                    )
                }
            }
        }
    }

    fun getColorWithAlpha(color: Int, ratio: Float): Int {
        var newColor = 0
        val alpha = Math.round(Color.alpha(color) * ratio).toInt()
        val r: Int = Color.red(color)
        val g: Int = Color.green(color)
        val b: Int = Color.blue(color)
        newColor = Color.argb(alpha, r, g, b)
        return newColor
    }

    fun navigateToGoogleMap(
        currentLat: String,
        currentLng: String,
        destinationLat: String,
        destinationLng: String
    ) {
        val uri =
            Constants.GoogleMap.LINK_URI + currentLat + "," + currentLng + Constants.GoogleMap.DESTINATION + destinationLat + "," + destinationLng
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage(Constants.GoogleMap.PACKAGE_NAME_GOOGLE_MAP)
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            showToast("Please install a Google map application", requireContext())
        }
    }

    fun handleIntent(intent: Intent?) {
        if (intent != null && intent.data != null) {
            if (BuildConfig.URI_SCHEME.equals(intent.data!!.scheme)) {
                UAEPassController.resume(intent.dataString)
            }
        }
    }

    //    fun showBottomSheet(
//        bottomSheetFragment: BottomSheetDialogFragment,
//        tag: String? = null
//    ) {
//        bottomSheetFragment.show(
//            requireActivity().supportFragmentManager, tag
//        )
//    }
    companion object {
        private const val TAG = "BaseFragment"
    }
}