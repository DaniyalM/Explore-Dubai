package com.app.dubaiculture.data.repository.explore.local.models

import com.app.dubaiculture.data.repository.explore.remote.response.ExploreDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Result(
    @SerializedName("Value")
    @Expose
    var value: List<ExploreDTO>)