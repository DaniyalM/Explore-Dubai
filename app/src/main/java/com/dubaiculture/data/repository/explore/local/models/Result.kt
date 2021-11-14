package com.dubaiculture.data.repository.explore.local.models

import com.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.dubaiculture.data.repository.explore.remote.response.ExploreDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Result(
    @SerializedName("Value")
    @Expose
    var value: List<ExploreDTO>,



    @SerializedName("AttractionCategories")
    @Expose
    var attractionsCategories: ArrayList<AttractionCategoryDTO>?=null,
    @SerializedName("Events")
    @Expose
    var events: ArrayList<EventsDTO>?=null,
)