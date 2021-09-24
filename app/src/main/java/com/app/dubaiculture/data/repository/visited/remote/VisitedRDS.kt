package com.app.dubaiculture.data.repository.visited.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.user.remote.request.GuestTokenRequestDTO
import com.app.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequestDTO
import com.app.dubaiculture.data.repository.viewgallery.service.ViewGalleryService
import com.app.dubaiculture.data.repository.visited.remote.request.AddVisitedPlacedRequestDTO
import com.app.dubaiculture.data.repository.visited.remote.service.VisitedService
import javax.inject.Inject

class VisitedRDS @Inject constructor(private val visitedService: VisitedService) :
    BaseRDS() {


    suspend fun addVisitedPlaces(addVisitedPlacedRequestDTO: AddVisitedPlacedRequestDTO) = safeApiCall {
        visitedService.addVisitedPlace(addVisitedPlacedRequestDTO)
    }
}