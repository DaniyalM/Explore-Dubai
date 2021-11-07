package com.dubaiculture.data.repository.viewgallery.remote.response

import com.dubaiculture.data.repository.sitemap.remote.response.IbeconDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewGalleryDTO (
    @SerializedName("ID")
    @Expose
    var id: String?=null,

    @SerializedName("Title")
    @Expose
    var title: String,

    @SerializedName("Description")
    @Expose
    var desc: String,
    @SerializedName("Images")
    @Expose
    var images: List<ImagesDTO>? = emptyList()

        )