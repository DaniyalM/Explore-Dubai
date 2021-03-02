package com.app.dubaiculture.data.repository.explore.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.service.ExploreService
import com.app.dubaiculture.data.repository.photo.remote.request.GetPhotoRequest
import javax.inject.Inject

class ExploreRDS @Inject constructor(private val exploreService: ExploreService) : BaseRDS() {
    suspend fun getExplore(culture : String) = safeApiCall {
        exploreService.getExploreApi(culture)
    }
}