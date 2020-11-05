package com.daniyal.basicappimpl.ui.home.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.hilt.lifecycle.ViewModelInject
import com.daniyal.basicappimpl.ui.base.BaseViewModel

class MainViewModel @ViewModelInject constructor(application: Application) :
        BaseViewModel(application) {

    init {
        showLoader(true)
        Handler(Looper.getMainLooper()).postDelayed({
            showLoader(false)
        }, 5000)
    }


}
