package com.dubaiculture.data.repository.visited.remote

import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.user.remote.request.GuestTokenRequestDTO
import com.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequestDTO
import com.dubaiculture.data.repository.viewgallery.service.ViewGalleryService
import com.dubaiculture.data.repository.visited.remote.request.AddVisitedPlacedRequestDTO
import com.dubaiculture.data.repository.visited.remote.service.VisitedService
import javax.inject.Inject

class VisitedRDS @Inject constructor(private val visitedService: VisitedService) :
    BaseRDS() {


    suspend fun addVisitedPlaces(addVisitedPlacedRequestDTO: AddVisitedPlacedRequestDTO) = safeApiCall {
        visitedService.addVisitedPlace(addVisitedPlacedRequestDTO)
    }
}