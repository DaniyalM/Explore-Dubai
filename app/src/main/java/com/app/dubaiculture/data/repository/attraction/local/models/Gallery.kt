package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gallery(
    var galleryImage: String? = "",
    var galleryThumbnail: String? = "",
    var galleryLink: String? = "",
    var isImage: Boolean = false,
) : Parcelable
