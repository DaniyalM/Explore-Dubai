package com.app.dubaiculture.data.repository.profile.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("Message")
        @Expose
        var message: String,
        @SerializedName("UserImage")
        @Expose
        var userImage: String
)