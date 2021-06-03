package com.app.dubaiculture.data.repository.attraction.remote.request

class AttractionRequestDTO(
    val attractionCategoryId: String?=null,
    val pageNumber: Int?=null,
    val pageSize: Int?=null,
     val culture: String = "en",
)