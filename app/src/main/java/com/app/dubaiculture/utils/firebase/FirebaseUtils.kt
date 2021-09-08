package com.app.dubaiculture.utils.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception

suspend fun getFcmToken(): String {
    return try {
        FirebaseMessaging.getInstance().token.await()
    } catch (exception: Exception) {
        ""
    }

}

fun subscribeToTopic(
        topic: String,
        successCallback: (() -> Unit)? = null,
        failureCallback: (() -> Unit)? = null
) {
    Firebase.messaging.subscribeToTopic(topic).addOnCompleteListener { task ->
        if (task.isComplete) {
            successCallback?.invoke()
            Timber.e("Subscription to topic $topic successful")
        } else {
            failureCallback?.invoke()
            Timber.e("Subscription to topic $topic failed")
        }
    }.addOnFailureListener {
        failureCallback?.invoke()
        Timber.e("Subscription to topic ${it.message} failed")
    }
}

fun unSubscribeFromTopic(
        topic: String,
        successCallback: (() -> Unit)? = null,
        failureCallback: (() -> Unit)? = null
) {
    Firebase.messaging.unsubscribeFromTopic(topic).addOnCompleteListener { task ->
        if (task.isComplete) {
            successCallback?.invoke()
            Timber.e("Unsub from topic $topic successful")
        } else {
            failureCallback?.invoke()
            Timber.e("Unsub from topic $topic failed")
        }
    }.addOnFailureListener {
        failureCallback?.invoke()
        Timber.e("Unsub from topic ${it.message} failed")
    }
}

