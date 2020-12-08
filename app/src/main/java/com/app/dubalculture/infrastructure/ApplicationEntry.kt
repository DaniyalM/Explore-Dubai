package com.app.dubalculture.infrastructure

import android.app.Application
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
        Timber.plant(Timber.DebugTree())

    }


}