package com.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.dubaiculture.data.repository.attraction.remote.response.ThreeSixtyImageItemDTO
import kotlinx.parcelize.Parcelize
@Parcelize
data class Assets360(
    val image: String? = null,
    val imageItems: List<ThreeSixtyImageItem>? = null,

    ) : Parcelable