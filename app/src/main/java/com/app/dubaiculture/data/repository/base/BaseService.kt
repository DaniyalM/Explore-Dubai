package com.app.dubaiculture.data.repository.base

import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequestDTO
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BaseService {
    @POST("/api/Content/AddFavorites")
    suspend fun addToFavourites(@Body addToFavouriteRequestDTO: AddToFavouriteRequestDTO): AddToFavouriteResponse
}