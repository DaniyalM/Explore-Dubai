package com.dubaiculture.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.dubaiculture.R

import java.util.*

class DcWidget : AppWidgetProvider() {
    val MyOnClick="MyClick"
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        val number = String.format("%03d", Random().nextInt(900) + 100)

        appWidgetIds?.forEach { appWidgetId ->

            // Create an Intent to launch ExampleActivity
//            val pendingIntent: PendingIntent = Intent(context, DcWidget::class.java)
//                .let { intent ->
//                    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
//                    PendingIntent.getBroadcast(
//                        context,
//                        0,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                    )
//                }

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views: RemoteViews = RemoteViews(
                context?.packageName,
                R.layout.dc_app_widget_layout
            ).apply {

                setOnClickPendingIntent(R.id.setupWidget,getPendingSelfIntent(context, MyOnClick))
                this.setTextViewText(R.id.text_widget, "Widget has $number")

            }




            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager?.updateAppWidget(appWidgetId, views)

        }

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        intent?.let {

            if (MyOnClick == it.action){
                //your onClick action is here
                Toast.makeText(context, "Widget has been updated! ", Toast.LENGTH_SHORT).show();

            }
        }


    }



    protected fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}