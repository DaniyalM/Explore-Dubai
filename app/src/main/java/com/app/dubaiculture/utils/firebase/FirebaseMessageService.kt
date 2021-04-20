package com.app.dubaiculture.utils.firebase

import android.app.PendingIntent
import android.os.Bundle
import com.app.dubaiculture.utils.PushNotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Timber.e("onNewToken => $token")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.e("Message received")
        var pendingIntent: PendingIntent? = null
        if (remoteMessage.data.isNotEmpty()) {
            //Data payload
            val extras = Bundle()
            for ((key, value) in remoteMessage.data) {
                extras.putString(key, value)
            }
        }
        //notification payload
        remoteMessage.notification?.apply {
            PushNotificationManager.showNotification(
                this@FirebaseMessageService,
                title,
                body,
                pendingIntent
            )
        }
    }
}