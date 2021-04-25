package com.app.dubaiculture.data.repository.viewgallery.local

import com.app.dubaiculture.data.repository.sitemap.remote.response.SiteMapAttractionDTO
import com.app.dubaiculture.data.repository.viewgallery.remote.response.ViewGalleryDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result (

    @SerializedName("ARMetadata")
    @Expose
    var viewGallery: ViewGalleryDTO,
)