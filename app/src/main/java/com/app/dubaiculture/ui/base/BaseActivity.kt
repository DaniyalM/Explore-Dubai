package com.app.dubaiculture.ui.base

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.CheckBox
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.dubaiculture.R
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.ProgressDialog
import com.app.dubaiculture.utils.event.EventUtilFunctions
import com.app.dubaiculture.utils.event.EventUtilFunctions.showAlert
import com.app.dubaiculture.utils.event.EventUtilFunctions.showLoader
import com.app.dubaiculture.utils.event.EventUtilFunctions.showSnackbar
import com.app.dubaiculture.utils.event.EventUtilFunctions.showToast
import com.app.dubaiculture.utils.event.UiEvent
import com.squareup.otto.Bus


abstract class BaseActivity : LocalizationActivity() {
    lateinit var applicationEntry: ApplicationEntry
    protected lateinit var bus: Bus
    protected var isBusRegistered: Boolean = false
    private var customProgressDialog: ProgressDialog? = null
    protected lateinit var navController: NavController

    lateinit var checkBox: CheckBox


//    override fun onStart() {
//        super.onStart()
//        adjustFontScale(resources.configuration)
//    }

//    override fun onResume() {
//        super.onResume()
//        adjustFontScale(resources.configuration)
//    }

    protected fun getDrawableFromId(resId: Int?): Drawable? {
        resId?.let {
            return if (it == 0) null
            else ResourcesCompat.getDrawable(resources, it, null)
        }
        return null
    }

    fun showAlert(
        message: String,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionNegative: (() -> Unit)? = null,
        actionPositive: (() -> Unit)? = null,
    ) {
        showAlert(
            message = message,
            context = this,
            title = title,
            textPositive = textPositive,
            textNegative = textNegative,
            actionPositive = actionPositive
        )
    }

    fun openEmailbox(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        startActivity(Intent.createChooser(intent, ""))
    }

    fun openDiallerBox(number: String? = null) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        startActivity(intent)
    }


    open fun adjustFontScale(configuration: Configuration) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        if (configuration.fontScale > 1.30) {
            configuration.fontScale = 1.30f
            val metrics = resources.displayMetrics
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager?
            wm!!.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            resources.updateConfiguration(configuration, metrics)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        applicationEntry = application as ApplicationEntry
        bus = applicationEntry.bus
        applicationEntry.auth.locale = getCurrentLanguage().language
        bus.register(this)
        isBusRegistered = true
        customProgressDialog = ProgressDialog(this)


    }

    fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        baseViewModel.uiEvents.observe(this, {
            it.getContentIfNotHandled()
                    ?.let { event ->
                        when (event) {
                            is UiEvent.ShowAlert -> {
                                showAlert(event.message, this)

                            }
                            is UiEvent.ShowToast -> {
                                showToast(event.message, this)
                            }
                            is UiEvent.ShowLoader -> {
                                showLoader(event.show, customProgressDialog)
                            }
                            is UiEvent.ShowSnackbar -> {
                                showSnackbar(
                                        findViewById(android.R.id.content),
                                        event.message,
                                        event.action
                                )
                            }
                            is UiEvent.ShowErrorDialog -> {
                                EventUtilFunctions.showErrorDialog(
                                        event.message,
                                        colorBg = event.colorBg,
                                        context = this
                                )
                            }
                        }
                    }
        })
    }




    override fun onDestroy() {
        super.onDestroy()

        if (customProgressDialog != null) {
            customProgressDialog = null
        }

        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }

    }

    override fun finish() {
        super.finish()
        if (customProgressDialog != null) {
            customProgressDialog = null
        }
        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }
    }

    fun navigate(@IdRes resId: Int, bundle: Bundle? = null) {
        navController.navigate(resId, bundle)
    }

    protected fun getNavControllerFun(int: Int): NavController {
        val navHostFragment =
                supportFragmentManager.findFragmentById(int) as NavHostFragment
        return navHostFragment.navController
    }




}