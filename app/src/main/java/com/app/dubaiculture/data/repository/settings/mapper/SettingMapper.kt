package com.app.dubaiculture.data.repository.settings.mapper

import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.data.repository.settings.remote.request.UserSettingsDTO

fun transformUserSettings(userSettingsDTO: UserSettingsDTO)= UserSettings(
        turnOnLocation = userSettingsDTO.TurnOnLocation,
        pushNotification = userSettingsDTO.PushNotification,
        email = userSettingsDTO.Email,
        locationBasedNotifications = userSettingsDTO.LocationBasedNotifications,
        sms = userSettingsDTO.SMS

)