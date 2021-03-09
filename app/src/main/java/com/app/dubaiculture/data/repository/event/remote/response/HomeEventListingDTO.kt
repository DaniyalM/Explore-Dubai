package com.app.dubaiculture.data.repository.event.remote.response

import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeEventListingDTO {
    @SerializedName("Category")
    @Expose
    var category: String? = null

    @SerializedName("Events")
    @Expose
    var events: List<UpComingEvents>? = null
}