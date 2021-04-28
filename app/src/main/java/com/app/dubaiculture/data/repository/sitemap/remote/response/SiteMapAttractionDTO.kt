package com.app.dubaiculture.data.repository.sitemap.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SiteMapAttractionDTO (
    @SerializedName("ID")
    @Expose
    var id: String?=null,

    @SerializedName("IBeacon")
    @Expose
    var iBeacon: IbeconDTO
)