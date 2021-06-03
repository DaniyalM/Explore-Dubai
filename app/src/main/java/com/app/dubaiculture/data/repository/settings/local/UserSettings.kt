package com.app.dubaiculture.data.repository.settings.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSettings(
        var turnOnLocation:Boolean=false,
        var pushNotification:Boolean=false,
        var locationBasedNotifications:Boolean=false,
        var email:Boolean=false,
        var sms:Boolean=false
):Parcelable