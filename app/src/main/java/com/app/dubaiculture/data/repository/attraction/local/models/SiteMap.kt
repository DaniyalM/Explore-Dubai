package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.app.dubaiculture.data.repository.attraction.remote.response.SiteMapsDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class SiteMap(
    val image: String? = null,
    val siteMap: List<SiteMaps>? = null,
) : Parcelable