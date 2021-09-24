package com.app.dubaiculture.data.repository.sitemap.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IBeaconsItemsDTO(
    @SerializedName("Step")
    @Expose
    var step: String?=null,
    @SerializedName("Title")
    @Expose
    var title: String?=null,
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
    var visitedOn: String?=null
)