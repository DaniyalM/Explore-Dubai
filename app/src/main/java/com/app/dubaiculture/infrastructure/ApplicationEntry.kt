package com.app.dubaiculture.infrastructure

import android.app.Application

import com.app.dubaiculture.utils.NetworkLiveData
import com.app.dubaiculture.utils.ThemeUtil
import com.squareup.otto.Bus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ApplicationEntry : Application() {
    var bus: Bus = Bus()
    lateinit var auth: AuthState

    override fun onCreate() {
        super.onCreate()
        auth = AuthState()
        NetworkLiveData.initNetwork(this)
        Timber.plant(Timber.DebugTree())
        ThemeUtil.applyTheme("light")
    }


}