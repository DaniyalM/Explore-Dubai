package com.app.dubaiculture.data.repository.attraction.local.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Gallery(
    var galleryImage: String = "",
    var galleryThumbnail: String = "",
    var galleryLink: String = "",
    var isImage: Boolean = false,
) {
}
