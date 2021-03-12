package com.app.dubaiculture.data.repository.attraction.remote.request

data class AttractionRequest(
                             val attractionId: String?="",
                             val pageNumber : Int?=null,
                             val pageSize:Int?=null,
                             val culture: String = "en",
                             )
