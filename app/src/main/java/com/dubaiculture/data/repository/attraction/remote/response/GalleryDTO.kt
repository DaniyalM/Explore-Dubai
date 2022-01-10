package com.dubaiculture.data.repository.attraction.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GalleryDTO {
    @SerializedName("GalleryImage")
    @Expose
    var galleryImage: String ?= ""

    @SerializedName("GalleryThumbnail")
    @Expose
    var galleryThumbnail: String ?= ""

    @SerializedName("GalleryLink")
    @Expose
    var galleryLink: String ?= ""

    @SerializedName("IsImage")
    @Expose
    var isImage: Boolean = false
}