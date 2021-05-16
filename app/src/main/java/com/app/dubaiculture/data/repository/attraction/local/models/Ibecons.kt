package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ibecons(
        var image : String?=null,
        var ibeconItems : List<IbeconITemsSiteMap> ? =null
): Parcelable