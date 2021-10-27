package com.app.dubaiculture.data.repository.profile.remote

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.app.dubaiculture.data.repository.popular_service.remote.response.EServiceDTO
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
        @SerializedName("Services")
        @Expose
        var events: List<EventsDTO>,
        @SerializedName("Services")
        @Expose
        var services: List<EServiceDTO>
)