package com.app.dubaiculture.data.repository.attraction.remote.response

import com.app.dubaiculture.data.repository.event.local.models.Events
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class AttractionDTO {


    @SerializedName("ID")
    @Expose
    var id: String? = null

    @SerializedName("Category")
    @Expose
    var category: String? = null

    @SerializedName("Title")
    @Expose
    var title: String? = null

    @SerializedName("LocationTitle")
    @Expose
    var locationTitle: String? = null

    @SerializedName("Location")
    @Expose
    var location: String? = null

    @SerializedName("PortraitImage")
    @Expose
    var portraitImage: String? = null

    @SerializedName("LandscapeImage")
    @Expose
    var landscapeImage: String? = null

    @SerializedName("Description")
    @Expose
    var description: String? = null

    @SerializedName("StartTime")
    @Expose
    var startTime: String? = null

    @SerializedName("EndTime")
    @Expose
    var endTime: String? = null

    @SerializedName("StartDay")
    @Expose
    var startDay: String? = null

    @SerializedName("EndDay")
    @Expose
    var endDay: String? = null

    @SerializedName("Color")
    @Expose
    var color: String? = null

    @SerializedName("Is_Favourite")
    @Expose
    var isFavourite: Boolean = false

    @SerializedName("Events")
    @Expose
    var events: List<Events> = emptyList()

}