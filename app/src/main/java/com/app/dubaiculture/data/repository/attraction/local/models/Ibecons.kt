package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.app.dubaiculture.data.repository.sitemap.local.BeaconItems
import kotlinx.parcelize.Parcelize
@Parcelize
data class Ibecons(
        var image : String?=null,
        var ibeconItems : List<BeaconItems> ? = mutableListOf()
): Parcelable