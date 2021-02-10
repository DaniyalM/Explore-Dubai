package com.app.dubaiculture.data.repository.explore.remote.response

import com.app.dubaiculture.data.repository.explore.local.models.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ExploreDTO(
    @SerializedName("Title")
    @Expose
    val title: String,
    @SerializedName("Category")
    @Expose
    val category: String,
    @SerializedName("Value")
    @Expose
    val value:List<BaseModel>
    )

