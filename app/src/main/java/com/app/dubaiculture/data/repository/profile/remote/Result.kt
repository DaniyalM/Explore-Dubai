package com.app.dubaiculture.data.repository.profile.remote

import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("Message")
        @Expose
        var message: String,
        @SerializedName("UserImage")
        @Expose
        var userImage: String,
        @SerializedName("Attractions")
        @Expose
        var attraction: List<AttractionDTO>,
        @SerializedName("Events")
        @Expose
        var events: List<EventsDTO>
)