package com.app.dubaiculture.data.repository.visited.remote.service

import com.app.dubaiculture.data.repository.user.remote.request.RefreshTokenRequestDTO
import com.app.dubaiculture.data.repository.user.remote.response.RefreshTokenResponse
import com.app.dubaiculture.data.repository.visited.remote.request.AddVisitedPlacedRequestDTO
import com.app.dubaiculture.data.repository.visited.remote.response.VisitedResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface VisitedService {

    @POST("Content/AddVisitedPlace")
    suspend fun addVisitedPlace(@Body addVisitedPlacedRequestDTO: AddVisitedPlacedRequestDTO): VisitedResponse

}