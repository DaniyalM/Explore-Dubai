package com.app.dubaiculture.utils.firebase

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

suspend fun getFcmToken(): String {
    return try {
        FirebaseMessaging.getInstance().token.await()
    } catch (exception: Exception) {
        ""
    }

}

