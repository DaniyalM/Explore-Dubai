package com.app.dubaiculture.data.repository.attraction.remote

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("AttractionCategories")
    @Expose
    var attractionsCategories: List<AttractionCategoryDTO>,

    @SerializedName("Attraction")
    @Expose
    var attraction: AttractionDTO,

    @SerializedName("Attractions")
    @Expose
    var attractions: List<AttractionDTO>,

    @SerializedName("Searches")
    @Expose
    var searches: List<String>,
    @SerializedName("message")
    @Expose
    var message: String,
)