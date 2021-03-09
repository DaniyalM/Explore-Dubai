package com.app.dubaiculture.data.repository.event.local.models

import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.app.dubaiculture.data.repository.event.remote.response.HomeEventListingDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("EventHomeListing")
    @Expose
    var homeEventListing: ArrayList<HomeEventListingDTO>,

    @SerializedName("Event")
    @Expose
    var event: EventsDTO,

    )