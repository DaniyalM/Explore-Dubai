package com.dubaiculture.data.repository.settings.remote

import com.dubaiculture.data.repository.settings.remote.request.UserSettingsDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("UserSettings")
        @Expose
        var userSettings: UserSettingsDTO
)