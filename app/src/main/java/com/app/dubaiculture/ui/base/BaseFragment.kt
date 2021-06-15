package com.app.dubaiculture.ui.base

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.NetworkRequest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.NetworkLiveData
import com.app.dubaiculture.utils.ProgressDialog
import com.app.dubaiculture.utils.event.EventUtilFunctions
import com.app.dubaiculture.utils.event.EventUtilFunctions.showToast
import com.app.dubaiculture.utils.event.UiEvent
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.event_search_toolbar.view.*
import java.util.*


abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    protected lateinit var application: ApplicationEntry
    protected var isBusRegistered: Boolean = false
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity

    protected var customProgressDialog: ProgressDialog? = null
    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var networkRequest: NetworkRequest


    lateinit var checkBox: CheckBox


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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)

    }

    override fun onStart() {
        super.onStart()
        adjustFontScale(resources.configuration)
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
                            is UiEvent.NavigateByBack -> {
                                navigateBack()
                            }
                            is UiEvent.NavigateByDirections -> {
                                navigateByDirections(event.navDirections)
                            }
                            is UiEvent.NavigateByAction -> {
                                navigateByAction(event.actionId, event.bundle)
                            }
                            is UiEvent.ShowErrorDialog -> {
                                EventUtilFunctions.showErrorDialog(
                                        event.message,
                                        colorBg = event.colorBg,
                                        context = activity
                                )
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
    fun navigateBack(){
        EventUtilFunctions.navigateBack(this)
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
            binding!!.root.materialCardView?.shapeAppearanceModel =
                    binding!!.root.materialCardView!!.shapeAppearanceModel
                            .toBuilder()
                            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                            .setTopRightCornerSize(radius)
                            .build()
        } else {
            binding!!.root.materialCardView?.shapeAppearanceModel =
                    binding!!.root.materialCardView!!.shapeAppearanceModel
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
            val wm = requireContext().getSystemService(WINDOW_SERVICE) as WindowManager?
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


    fun openEmailbox(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        requireActivity().startActivity(Intent.createChooser(intent, ""))
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

    fun navigateToGoogleMap(currentLat: String, currentLng: String, destinationLat: String, destinationLng: String) {
        val uri = Constants.GoogleMap.LINK_URI + currentLat + "," + currentLng + Constants.GoogleMap.DESTINATION + destinationLat + "," + destinationLng
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage(Constants.GoogleMap.PACKAGE_NAME_GOOGLE_MAP)
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            showToast("Please install a Google map application", requireContext())
        }
    }

    protected fun openFragment(fragment: Fragment, tag: String?) {
        requireActivity().supportFragmentManager.beginTransaction()
                .add(fragment, tag)
                .addToBackStack(null)
                .commit();
    }

}