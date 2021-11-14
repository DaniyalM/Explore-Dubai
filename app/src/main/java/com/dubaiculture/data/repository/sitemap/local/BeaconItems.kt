package com.dubaiculture.data.repository.sitemap.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class BeaconItems(
    val step: String? = null,
    val title: String? = null,
    val image: String? = null,
    val thumbnail: String? = null,
    val summary: String? = null,
    val deviceID: String? = null,
    val visited: Boolean,
    val visitedOn: String,
    var isVisited: Boolean = false,
    val id: Int
) : Parcelable