package com.app.dubaiculture.data.repository.sitemap.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class IbeconITemsSiteMap(
    val step : String ? =null,
    val title : String ? =null,
    val image : String ? =null,
    val thumbnail : String ? =null,
    val summary : String ? =null,
    val deviceID : String ? =null,
    var isVisited : Boolean  =false
    ) : Parcelable