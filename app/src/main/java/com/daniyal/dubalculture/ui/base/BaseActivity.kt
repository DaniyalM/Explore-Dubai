package com.daniyal.dubalculture.ui.base

import android.os.Bundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.daniyal.dubalculture.infrastructure.ApplicationEntry
import com.daniyal.dubalculture.utils.ProgressDialog
import com.daniyal.dubalculture.utils.event.EventUtilFunctions.showAlert
import com.daniyal.dubalculture.utils.event.EventUtilFunctions.showLoader
import com.daniyal.dubalculture.utils.event.EventUtilFunctions.showSnackbar
import com.daniyal.dubalculture.utils.event.EventUtilFunctions.showToast
import com.daniyal.dubalculture.utils.event.UiEvent
import com.squareup.otto.Bus


abstract class BaseActivity : LocalizationActivity() {
    lateinit var applicationEntry: ApplicationEntry
    protected lateinit var bus: Bus
    protected var isBusRegistered: Boolean = false
    private var customProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationEntry = application as ApplicationEntry
        bus = applicationEntry.bus
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
                            showAlert(event.message)
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
}