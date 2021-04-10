package com.app.dubaiculture.data.repository.attraction.remote.response

import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AttractionDTO {


    @SerializedName("ID")
    @Expose
    var id: String = ""

    @SerializedName("Category")
    @Expose
    var category: String = ""

    @SerializedName("Title")
    @Expose
    var title: String = ""

    @SerializedName("LocationTitle")
    @Expose
    var locationTitle: String? = null

    @SerializedName("Location")
    @Expose
    var location: String? = null

    @SerializedName("Latitude")
    @Expose
    var latitude: String? = "24.83250180519734"

    @SerializedName("Longitude")
    @Expose
    var longitude: String? = "67.08119661055807"

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

    @SerializedName("IsFavourite")
    @Expose
    var isFavourite: Boolean = false

    @SerializedName("Events")
    @Expose
    var events: List<EventsDTO>? = null

    @SerializedName("SocialLinks")
    @Expose
    var socialLinks: List<SocialLinkDTO>? = null

    @SerializedName("Gallery")
    @Expose
    var gallery: List<GalleryDTO>? = null

    @SerializedName("360Assets")
    @Expose
    var asset360: Assets360DTO? = null


}