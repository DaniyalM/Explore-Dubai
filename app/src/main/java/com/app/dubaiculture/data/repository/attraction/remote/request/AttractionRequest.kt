package com.app.dubaiculture.data.repository.attraction.remote.request

data class AttractionRequest(val culture: String = "en",
                             val attractionId: String?="",
                             val attractionCatId: String?="")
