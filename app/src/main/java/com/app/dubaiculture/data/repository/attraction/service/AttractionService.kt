package com.app.dubaiculture.data.repository.attraction.service

import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionCategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AttractionService {
    @POST("/category/attractions")
    suspend fun getAttractionCategoryApi(@Body attractionCategoryRequestDTO: AttractionCategoryRequestDTO): AttractionCategoryResponse
}