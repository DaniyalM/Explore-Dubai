package com.dubaiculture.utils.firebase

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import com.dubaiculture.R
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.navGraphActivity.NavGraphActivity
import com.dubaiculture.ui.postLogin.notifications.enums.NotificationTypes
import com.dubaiculture.ui.preLogin.PreLoginActivity
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.FCM.Key.NOTIFICATION_ITEM
import com.dubaiculture.utils.Constants.NavBundles.HANDLE_PUSH
import javax.inject.Inject
import kotlin.random.Random

class FirebaseNavigationHandler @Inject constructor(
    private val context: Application
) {
    fun getPendingIntent(extras: Bundle): PendingIntent? {
        if (extras.containsKey(NOTIFICATION_ITEM) && NotificationTypes.shouldNavigateToAttractionDetail(
                extras.get(Constants.FCM.Key.NOTIFICATION_TYPE).toString().toInt()
            )
        ) {
            return getAttractionDetailIntent(extras = extras)
        } else if (extras.containsKey(NOTIFICATION_ITEM) && NotificationTypes.shouldNavigateToEventDetail(
                extras.get(Constants.FCM.Key.NOTIFICATION_TYPE).toString().toInt()
            )
        ) {
            return getEventDetailIntent(extras = extras)
        } else if (extras.containsKey(NOTIFICATION_ITEM) && NotificationTypes.shouldNavigateToGeneralNotification(
                extras.get(Constants.FCM.Key.NOTIFICATION_TYPE).toString().toInt()
            )
        ) {
            return getGeneralDetailIntent(extras = extras)
        } else if (extras.containsKey(NOTIFICATION_ITEM) && NotificationTypes.shouldNavigateToTripNotification(
                extras.get(Constants.FCM.Key.NOTIFICATION_TYPE).toString().toInt()
            )
        ) {
            return getTripDetailIntent(extras = extras)
        } else if (extras.containsKey(NOTIFICATION_ITEM) && NotificationTypes.shouldNavigateToApplication(
                extras.get(Constants.FCM.Key.NOTIFICATION_TYPE).toString().toInt()
            )
        ) {
            return getGeneralDetailIntent(extras = extras)
        }
        return null
    }

    private fun getEventDetailIntent(extras: Bundle): PendingIntent? {
        val graphId = R.navigation.event_detail_navigation
        val intent = getIntent()
        intent.putExtra(Constants.NavBundles.GRAPH_ID, graphId)
        val discussionId = extras.getString(NOTIFICATION_ITEM) ?: return null
        intent.putExtra(Constants.FCM.Key.EVENT_ID, discussionId)
        return getPendingIntent(intent)
    }

    private fun getGeneralDetailIntent(extras: Bundle): PendingIntent? {
        val graphId = R.navigation.explore_navigation
        val intent = getIntent()
        val discussionId = extras.getString(NOTIFICATION_ITEM) ?: return null
        intent.putExtra(Constants.NavBundles.GRAPH_ID, R.navigation.explore_navigation)

        intent.putExtra(Constants.FCM.Key.GENERAL_ID, extras)
        return getPendingIntent(intent)
    }

    private fun getTripDetailIntent(extras: Bundle): PendingIntent? {
        val graphId = R.navigation.trip_detail_navigation
        val intent = getIntent()
        val discussionId = extras.getString(NOTIFICATION_ITEM)?.toInt() ?: return null
        intent.putExtra(Constants.NavBundles.GRAPH_ID, graphId)

        intent.putExtra(Constants.FCM.Key.TRIP_ID, discussionId)
        return getPendingIntent(intent)
    }

    private fun getAttractionDetailIntent(extras: Bundle): PendingIntent? {
        val graphId = R.navigation.attraction_detail_navigation
        val intent = getIntent()

        val discussionId = extras.getString(NOTIFICATION_ITEM) ?: return null
        intent.putExtra(Constants.NavBundles.GRAPH_ID, graphId)

        intent.putExtra(Constants.FCM.Key.ATTRACTION_ID, discussionId)
        return getPendingIntent(intent)
    }

    private fun getIntent(): Intent {
        val appStarted = (context as ApplicationEntry).appStarted
        return if (appStarted) {
            val intent = Intent(context, NavGraphActivity::class.java)
            intent.action = (System.currentTimeMillis().toString())
            intent
        } else {
            val intent = Intent(context, PreLoginActivity::class.java)
            intent.putExtra(HANDLE_PUSH, true)
            intent.action = (System.currentTimeMillis().toString())
            intent
        }
    }

    //unique identifier & flag returns new intent instead of providing previous PI
    private fun getPendingIntent(intent: Intent): PendingIntent {
        return PendingIntent.getActivity(
            context,
            Random.nextInt(999999999),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}