package com.dubaiculture.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import java.text.SimpleDateFormat
import java.util.*


object AppConfigUtils {
    var BASE_URL = "https://jsonplaceholder.typicode.com/"
    var clickCheckerFlag: Int = 0
    var favouriteClickCheckerFlag: Int = 0

    //    var serviceClickCheckerFlag: Int = 0
    var SERVICE_DETAIL_HEADER_FLAG: Int = 0


    // Name of Notification Channel for verbose notifications of background work
    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence = "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
    val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    // The name of the image manipulation work
    const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"
    const val DELAY_TIME_MILLIS: Long = 3000
    const val TAG_OUTPUT = "OUTPUT"
    const val KEY_IMAGE_URI = "KEY_IMAGE_URI"

    fun getDrawable(context: Context, drawableResId: Int, colorFilter: Int): Drawable {
        val drawable = getDrawable(context, drawableResId)
        drawable?.setColorFilter(colorFilter, PorterDuff.Mode.SRC_IN)
        return drawable!!
    }

    fun getDrawable(context: Context, drawableResId: Int): Drawable? {
        return VectorDrawableCompat.create(context.resources, drawableResId, context.theme)
    }

    fun getMarkerBitmapFromView(@DrawableRes resId: Int, context: Context): Bitmap? {
        val customMarkerView: View =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)!!.inflate(
                R.layout.view_custom_marker,
                null
            )
        val markerImageView: ImageView =
            customMarkerView.findViewById(R.id.profile_image) as ImageView
        markerImageView.setImageResource(resId)
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMarkerView.layout(
            0,
            0,
            customMarkerView.measuredWidth,
            customMarkerView.measuredHeight
        )
        customMarkerView.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(
            customMarkerView.measuredWidth, customMarkerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable: Drawable = customMarkerView.background
        if (drawable != null) drawable.draw(canvas)
        customMarkerView.draw(canvas)
        return returnedBitmap
    }


    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun shareLink(stringUrl: String, activity: Activity) {

        if (stringUrl.isNotEmpty()) {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing News")
            i.putExtra(Intent.EXTRA_TEXT, BuildConfig.BASE_URL_SHARE + stringUrl)
            activity.startActivity(Intent.createChooser(i, "Share URL"))
        }
    }


}