package com.app.dubaiculture.data.repository.sitemap.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IbeconDTO(
    @SerializedName("Image")
    @Expose
    var img: String?=null,
    @SerializedName("IBeaconsItems")
    @Expose
    var iBeaconsItems: List<IBeaconsItemsDTO>?= emptyList()
)