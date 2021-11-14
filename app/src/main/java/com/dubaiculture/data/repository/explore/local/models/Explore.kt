package com.dubaiculture.data.repository.explore.local.models


data class Explore(
    var title: String = "",
    var category: String= "",
    var value: List<BaseModel>
)
