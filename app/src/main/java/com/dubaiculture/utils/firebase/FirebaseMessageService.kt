package com.dubaiculture.utils.firebase

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.dubaiculture.R
import com.dubaiculture.ui.preLogin.PreLoginActivity
import com.dubaiculture.utils.Constants.NavBundles.EVENT_ID
import com.dubaiculture.utils.PushNotificationManager
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
            val intent = Intent(applicationContext, PreLoginActivity::class.java)
            val bundle = bundleOf(EVENT_ID to "E0FB335B7B464F1F912A1C7C62B507FE")
//             pendingIntent =
//                PendingIntent.getActivity(applicationContext,1,intent, PendingIntent.FLAG_UPDATE_CURRENT)
            pendingIntent = NavDeepLinkBuilder(applicationContext)
                .setGraph(R.navigation.post_login_navigation)
                .setDestination(R.id.event_detail_navigation)
                .setArguments(bundle)
                .createPendingIntent()
            PushNotificationManager.showNotification(
                this@FirebaseMessageService,
                title,
                body,
                pendingIntent
            )
        }
    }
}