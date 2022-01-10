package com.dubaiculture.data.repository.attraction.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Assets360DTO(
    @SerializedName("Image")
    @Expose
    val image: String? = null,

    @SerializedName("ThreeSixtyImageItems")
    @Expose
    val imageItems: List<ThreeSixtyImageItemDTO>? = null,

    ) : Parcelable