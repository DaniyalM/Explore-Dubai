package com.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ThreeSixtyImageItem(
     val title: String? = null,
     val image: String? = null,
     val xAxis: String? = null,
     val yAxis: String? = null,
    ) : Parcelable

