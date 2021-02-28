package com.app.dubaiculture.data.repository.attraction.service

import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryResponse
import retrofit2.http.GET

interface AttractionService {
    @GET("/category/attractions")
    fun getAttractionCategoryApi(attractionCategoryRequestDTO: AttractionCategoryRequestDTO): AttractionCategoryResponse
}