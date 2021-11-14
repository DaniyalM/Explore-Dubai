package com.dubaiculture.data.repository.popular_service.local.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PopularServices(
    @SerializedName("ID")
    @Expose
    var id: String? = "",

    @SerializedName("Title")
    @Expose
    val title: String? = "",

    @SerializedName("ColoredIcon")
    @Expose
    var coloredIcon: String? = "",

    @SerializedName("JsonFile")
    @Expose
    var jsonFile: String? = "",

    @SerializedName("HoveredJsonFile")
    @Expose
    var hoveredJsonFile: String? = "",

    @SerializedName("PlayJson")
    @Expose
    var playJson: Boolean? = false,

    @SerializedName("Icon")
    @Expose
    var icon: String? = null,
)


