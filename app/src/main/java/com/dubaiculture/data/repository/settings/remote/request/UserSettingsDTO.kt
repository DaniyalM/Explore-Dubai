package com.dubaiculture.data.repository.settings.remote.request

data class UserSettingsDTO(
        val TurnOnLocation:Boolean=false,
        val PushNotifications :Boolean=false,
        val LocationBasedNotifications:Boolean=false,
        val Email:Boolean=false,
        val SMS:Boolean=false,
        val DefaultLanguage:String,

)

//"TurnOnLocation": false,
//"PushNotifications": false,
//"LocationBasedNotifications": false,
//"Email": false,
//"SMS": false