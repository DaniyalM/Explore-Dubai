package com.app.dubaiculture.data.repository.attraction.remote.request

class AttractionRequestDTO(
    val attractionCategoryId: String,
    val pageNumber: Int,
    val pageSize: Int,
     val culture: String = "en",
)