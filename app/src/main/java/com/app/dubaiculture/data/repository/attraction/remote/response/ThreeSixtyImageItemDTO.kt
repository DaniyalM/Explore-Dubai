package com.app.dubaiculture.data.repository.attraction.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThreeSixtyImageItemDTO(
    @SerializedName("Title")
    @Expose
     val title: String? = null,

    @SerializedName("Image")
    @Expose
     val image: String? = null,

    @SerializedName("XAxis")
    @Expose
     val xAxis: String? = null,

    @SerializedName("YAxis")
    @Expose
     val yAxis: String? = null,
) : Parcelable

