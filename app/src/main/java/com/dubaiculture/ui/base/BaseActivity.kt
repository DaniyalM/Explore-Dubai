package com.dubaiculture.ui.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.CheckBox
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dubaiculture.R
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.preLogin.login.LoginFragment
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.ProgressDialog
import com.dubaiculture.utils.event.EventUtilFunctions
import com.dubaiculture.utils.event.EventUtilFunctions.showAlert
import com.dubaiculture.utils.event.EventUtilFunctions.showLoader
import com.dubaiculture.utils.event.EventUtilFunctions.showSnackbar
import com.dubaiculture.utils.event.EventUtilFunctions.showToast
import com.dubaiculture.utils.event.UiEvent
import com.squareup.otto.Bus
import com.facebook.appevents.AppEventsLogger
import com.github.javiersantos.appupdater.AppUpdater


abstract class BaseActivity : LocalizationActivity() {
    lateinit var applicationEntry: ApplicationEntry
    protected lateinit var bus: Bus
    protected var isBusRegistered: Boolean = false
    private var customProgressDialog: ProgressDialog? = null
    protected lateinit var navController: NavController


    lateinit var checkBox: CheckBox
    protected var mPrevConfig: Configuration? = null

    override fun onResume() {
        super.onResume()
        val appUpdater = AppUpdater(this)
            .setCancelable(true)
            .setButtonDismiss("")
            .setButtonDoNotShowAgain("")
        appUpdater.start()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChanged(newConfig)
        mPrevConfig = Configuration(newConfig)

    }

    open fun logSentFriendRequestEvent() {
        val fbLogger = AppEventsLogger.newLogger(baseContext)
        fbLogger.logEvent("sentFriendRequest")
    }

    protected fun configurationChanged(newConfig: Configuration?) {
        newConfig?.let {
            if (isNightConfigChanged(it)) { // night mode has changed
                applicationEntry.preferenceRepository.isDarkTheme = isOnDarkMode(it)
                recreate()
                // do your thing
            }
        }

    }

    protected fun isNightConfigChanged(newConfig: Configuration): Boolean {
        return newConfig.diff(mPrevConfig) and ActivityInfo.CONFIG_UI_MODE != 0 && isOnDarkMode(
            newConfig
        ) != isOnDarkMode(mPrevConfig!!)
    }

    fun isOnDarkMode(configuration: Configuration): Boolean {
        return configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    protected fun getDrawableFromId(resId: Int?): Drawable? {
        resId?.let {
            return if (it == 0) null
            else ResourcesCompat.getDrawable(resources, it, null)
        }
        return null
    }


    fun showAlert(
        message: String = "",
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionNegative: (() -> Unit)? = null,
        actionPositive: (() -> Unit)? = null,
        isInternet: Boolean = false,
        application: ApplicationEntry? = null
    ) {
        showAlert(
            message = message,
            context = this,
            title = title,
            textPositive = textPositive,
            textNegative = textNegative,
            actionPositive = actionPositive,
            isInternet = isInternet,
            application = application

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


    fun isDark(configuration: Configuration) {
        val preferenceRepository = applicationEntry.preferenceRepository
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nightMode = configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
            when (nightMode) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    // Night mode is active, we're using dark theme
                    preferenceRepository.isDarkTheme = true
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    // Night mode is not active, we're using the light theme
                    preferenceRepository.isDarkTheme = false
                }
                else -> {
                    preferenceRepository.isDarkTheme = false
//                   preferenceRepository.isDarkTheme = isNightModeActive(this)
                }
            }
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationEntry = application as ApplicationEntry
        applicationEntry.preferenceRepository.isDarkTheme = isOnDarkMode(resources.configuration)
        mPrevConfig = Configuration(resources.configuration)
//        isDark(mPrevConfig!!)

        darkModeAccess()
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
            if (customProgressDialog!!.isShowing) {
                customProgressDialog!!.dismiss()
            }
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
        navController = navHostFragment.navController
        return navController
    }

    fun darkModeAccess() {
        (application as ApplicationEntry).preferenceRepository
            .nightModeLive.observe(this) { nightMode ->
                nightMode?.let { delegate.localNightMode = it }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LoginFragment().apply {
            handleIntent(intent)
        }
    }


}