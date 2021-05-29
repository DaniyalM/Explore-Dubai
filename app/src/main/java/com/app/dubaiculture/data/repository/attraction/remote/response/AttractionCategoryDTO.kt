package com.app.dubaiculture.data.repository.attraction.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class AttractionCategoryDTO(
    @SerializedName("ID")
    @Expose
    var id: String? = null,

    @SerializedName("Icon")
    @Expose
    var icon: String? = null,

    @SerializedName("Title")
    @Expose
    var title: String? = null,

    @SerializedName("WhiteIcon")
    @Expose
    var selectedSvg: String? = null,

    @SerializedName("NonWhiteIcon")
    @Expose
    var unselectedSvg: String? = null,



    @SerializedName("Attractions")
    @Expose
    var attractions: ArrayList<AttractionDTO>,
    @SerializedName("Color")
    @Expose
    var color: String? = null,


    )


