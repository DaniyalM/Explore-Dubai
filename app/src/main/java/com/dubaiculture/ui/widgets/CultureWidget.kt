package com.dubaiculture.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dubaiculture.R
import com.dubaiculture.ui.widgets.communication.WidgetWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */

@AndroidEntryPoint
class CultureWidget : AppWidgetProvider() {
    @Inject
    lateinit var workManager: WorkManager


    val dispatchAction = "DispatchAction"


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        val views: RemoteViews = RemoteViews(context.packageName, R.layout.culture_widget).apply {
            setOnClickPendingIntent(R.id.setupWidget, getPendingSelfIntent(context, dispatchAction))
        }

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(appWidgetManager, appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.let {
            if (dispatchAction == it.action) {
                Toast.makeText(context, "Action Dispatched", Toast.LENGTH_SHORT).show()
                doWork()
            }
        }
    }


    private fun doWork() {
        val request = OneTimeWorkRequestBuilder<WidgetWorker>().build()
        workManager.enqueue(request)
    }

    private fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

}

internal fun updateAppWidget(
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    views: RemoteViews
) {

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

