package com.dubaiculture.infrastructure

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.dubaiculture.utils.NetworkLiveData
import com.dubaiculture.utils.PreferenceRepository
import com.dubaiculture.utils.PushNotificationManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.squareup.otto.Bus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class ApplicationEntry : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    var bus: Bus = Bus()
    lateinit var auth: AuthState
    var isInternetActive = false
    lateinit var preferenceRepository: PreferenceRepository
    var appStarted: Boolean = false
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate() {
        super.onCreate()
        auth = AuthState()
        NetworkLiveData.initNetwork(this)
        PushNotificationManager.createNotificationChannel(this)
        isInternetActive = NetworkLiveData.isInternetAvailable()
        Timber.plant(Timber.DebugTree())
        preferenceRepository = PreferenceRepository(
            getSharedPreferences(DEFAULT_PREFERENCES, MODE_PRIVATE)
        )
        firebaseAnalytics = Firebase.analytics
    }




    companion object {
        const val DEFAULT_PREFERENCES = "default_preferences"
    }

    override fun getWorkManagerConfiguration()=
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}