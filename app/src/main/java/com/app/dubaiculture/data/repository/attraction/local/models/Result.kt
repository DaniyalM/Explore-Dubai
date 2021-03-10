package com.app.dubaiculture.data.repository.attraction.local.models

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("AttractionsCategories")
    @Expose
    var attractionsCategories: ArrayList<AttractionCategoryDTO>,

    @SerializedName("Attraction")
    @Expose
    var attraction: AttractionDTO,

    @SerializedName("Attractions")
    @Expose
    var attractions: List<AttractionDTO>,


    )