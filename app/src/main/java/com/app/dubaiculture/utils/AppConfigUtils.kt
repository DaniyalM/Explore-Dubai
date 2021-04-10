package com.app.dubaiculture.utils

import androidx.databinding.BindingAdapter
import com.app.dubaiculture.BuildConfig
import com.bumptech.glide.Glide
import com.rishabhharit.roundedimageview.RoundedImageView

object AppConfigUtils {
    var BASE_URL = "https://jsonplaceholder.typicode.com/"
    var clickCheckerFlag: Int = 0


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





}