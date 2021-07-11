package com.app.dubaiculture.data.repository.attraction.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SiteMapDTO(
    @SerializedName("Image")
    @Expose
    val image: String? = null,
    @SerializedName("SiteMaps")
    @Expose
    val siteMapDTOS: List<SiteMapsDTO>? = null,
    ) : Parcelable