package com.app.dubaiculture.data.repository.settings.local

data class UserSettings(
        val turnOnLocation:Boolean=false,
        val pushNotification:Boolean=false,
        val locationBasedNotifications:Boolean=false,
        val email:Boolean=false,
        val sms:Boolean=false
)