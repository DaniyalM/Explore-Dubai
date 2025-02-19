package com.dubaiculture.data.repository.settings.mapper

import com.dubaiculture.data.repository.settings.local.UserSettings
import com.dubaiculture.data.repository.settings.remote.request.UserSettingsDTO

fun transformUserSettings(userSettingsDTO: UserSettingsDTO)= UserSettings(
        turnOnLocation = userSettingsDTO.TurnOnLocation,
        pushNotification = userSettingsDTO.PushNotifications,
        email = userSettingsDTO.Email,
        locationBasedNotifications = userSettingsDTO.LocationBasedNotifications,
        sms = userSettingsDTO.SMS

)

fun transformUserSettingRequest(userSettings: UserSettings)=UserSettingsDTO(
        TurnOnLocation = userSettings.turnOnLocation,
        PushNotifications = userSettings.pushNotification,
        LocationBasedNotifications = userSettings.locationBasedNotifications,
        Email = userSettings.email,
        SMS = userSettings.sms,
        DefaultLanguage = userSettings.culture
)