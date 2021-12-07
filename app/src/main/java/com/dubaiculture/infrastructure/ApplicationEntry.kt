package com.dubaiculture.infrastructure

import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.dubaiculture.utils.BeaconUtils
import com.dubaiculture.utils.NetworkLiveData
import com.dubaiculture.utils.PreferenceRepository
import com.dubaiculture.utils.PushNotificationManager
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.service.BeaconManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.squareup.otto.Bus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger




@HiltAndroidApp
class ApplicationEntry : Application() {
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


//        isDark()

//        preferenceRepository.isDarkTheme = true

    }

//    fun isDark() {
//        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_NO -> {
//                preferenceRepository.isDarkTheme = false
//            } // Night mode is not active, we're using the light theme
//            Configuration.UI_MODE_NIGHT_YES -> {
//                preferenceRepository.isDarkTheme = true
//            } // Night mode is active, we're using dark theme
//        }
//
//    }




    companion object {
        const val DEFAULT_PREFERENCES = "default_preferences"
    }

}