package com.dubaiculture.data.repository.attraction.remote

import com.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryDTO
import com.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.dubaiculture.data.repository.search.local.SearchHeaderDTO
import com.dubaiculture.data.repository.search.local.SearchResultDTO
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

    @SerializedName("SearchResults")
    @Expose
    var searchesResultItem: SearchResultDTO,

    @SerializedName("Headers")
    @Expose
    var headers: List<SearchHeaderDTO>,
)