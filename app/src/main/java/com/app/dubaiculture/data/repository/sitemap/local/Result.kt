package com.app.dubaiculture.data.repository.sitemap.local

import com.app.dubaiculture.data.repository.sitemap.remote.response.SiteMapAttractionDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result (

    @SerializedName("Attraction")
    @Expose
    var attractions: SiteMapAttractionDTO,
)