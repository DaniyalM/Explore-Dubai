package com.app.dubaiculture.data.repository.attraction.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SiteMapsDTO(
    @SerializedName("Step")
    @Expose
    val step : String ? =null,
    @SerializedName("Title")
    @Expose
    val title : String ? =null
): Parcelable