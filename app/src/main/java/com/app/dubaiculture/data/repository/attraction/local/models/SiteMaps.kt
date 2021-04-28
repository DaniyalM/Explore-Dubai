package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SiteMaps(
    val step : String ? =null,
    val title : String ? =null
): Parcelable