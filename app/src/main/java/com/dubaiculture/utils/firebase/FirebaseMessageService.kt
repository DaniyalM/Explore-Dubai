package com.dubaiculture.utils.firebase

import android.app.PendingIntent
import android.content.Intent
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.PushNotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class FirebaseMessageService : FirebaseMessagingService() {

    @Inject
    lateinit var navigationHandler: FirebaseNavigationHandler
    override fun onNewToken(token: String) {
        Timber.e("onNewToken => $token")
    }


    override fun handleIntent(intent: Intent) {
        Timber.e("Message received by intent")
        Timber.e(intent.extras?.toString())
        val pendingIntent: PendingIntent? = try {
                navigationHandler.getPendingIntent(
                    intent.extras ?: return
                )
            } catch (exception: Exception) {
                null
            }

        PushNotificationManager.showNotification(
            this@FirebaseMessageService,
            intent.extras?.getString(Constants.FCM.Key.NOTIF_TITLE) ?: return,
            intent.extras?.getString(Constants.FCM.Key.NOTIF_BODY) ?: return,
            pendingIntent,
            Random.nextInt(999999999)
        )
    }
}