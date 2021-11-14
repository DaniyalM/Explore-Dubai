package com.dubaiculture.data.repository.profile.remote

import com.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.dubaiculture.data.repository.popular_service.remote.response.EServiceDTO
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
        var events: List<EventsDTO>,
        @SerializedName("Services")
        @Expose
        var services: List<EServiceDTO>
)