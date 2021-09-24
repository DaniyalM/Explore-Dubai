package com.app.dubaiculture.data.repository.attraction.remote.response

import android.os.Parcelable
import com.app.dubaiculture.data.repository.sitemap.remote.response.IBeaconsItemsDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class IbeconDTO(
        @SerializedName("Image")
        @Expose
        val image: String? = null,
        @SerializedName("IBeaconsItems")
        @Expose
        val iBeaconsItems: List<IBeaconsItemsDTO> = mutableListOf(),
)