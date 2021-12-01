package com.dubaiculture.data.repository.sitemap.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IBeaconsItemsDTO(
    @SerializedName("Step")
    @Expose
    var step: String?=null,
    @SerializedName("Title")
    @Expose
    var title: String?=null,
    @SerializedName("Subtitle")
    @Expose
    var subtitle: String?=null,
    @SerializedName("Image")
    @Expose
    var img: String?=null,
    @SerializedName("Thumbnail")
    @Expose
    var thumbnail: String?=null,
    @SerializedName("Summary")
    @Expose
    var summary: String?=null,
    @SerializedName("DeviceID")
    @Expose
    var deviceID: String?=null,
    @SerializedName("Visited")
    @Expose
    var visited: Boolean?=null,
    @SerializedName("VisitedOn")
    @Expose
    var visitedOn: String?=null,
    @SerializedName("ProximityID")
    @Expose
    var proximityID: String?=null,
    @SerializedName("ID")
    @Expose
    var ItemId: String?=null,
    @SerializedName("Minor")
    @Expose
    var minor: String?=null,
    @SerializedName("Major")
    @Expose
    var major: String?=null,
    @SerializedName("Serial")
    @Expose
    var serial: String?=null
)