package com.dubaiculture.data.repository.sitemap.local

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class BeaconItems(
    val step: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val image: String? = null,
    val thumbnail: String? = null,
    val summary: String? = null,
    val deviceID: String? = null,
    val visited: Boolean,
    val visitedOn: String,
    var isVisited: Boolean = false,
    var proximityID: String ,
    val id: Int,
    var minor: String,
    var major: String,
    var serial: String,
    var itemId: String
) : Parcelable