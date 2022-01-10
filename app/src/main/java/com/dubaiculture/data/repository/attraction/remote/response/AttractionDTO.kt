package com.dubaiculture.data.repository.attraction.remote.response

import com.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AttractionDTO {


    @SerializedName("ID")
    @Expose
    var id: String ? = null

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

    @SerializedName("MapIconColored")
    @Expose
    var mapIconColored: String? = null

    @SerializedName("MapIconGreyscale")
    @Expose
    var mapIconGreyscale: String? = null

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

    @SerializedName("Summary")
    @Expose
    var summary: String? = null

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

    @SerializedName("SiteMap")
    @Expose
    var siteMapDTO: SiteMapDTO? = null

    @SerializedName("EmailContact")
    @Expose
    var emailContact: String = ""

    @SerializedName("NumberContact")
    @Expose
    var numberContact: String = ""

    @SerializedName("Type")
    @Expose
    var type: String ?= null


    @SerializedName("IBeacon")
    @Expose
    var ibecon: IbeconDTO? = null

    @SerializedName("VisitedDateTime")
    @Expose
    var visitedDateTime: String? = "12 Nov, 2020"

    @SerializedName("RelatedEventsTitle")
    @Expose
    var RelatedEventsTitle: String? = ""

    @SerializedName("URL")
    @Expose
    var URL: String? = ""

    @SerializedName("TripAdvisorLink")
    @Expose
    var tripAdvisorLink: String? = ""
}