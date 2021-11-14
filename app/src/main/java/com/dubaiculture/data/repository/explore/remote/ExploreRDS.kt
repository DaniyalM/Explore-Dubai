package com.dubaiculture.data.repository.explore.remote

import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.dubaiculture.data.repository.explore.service.ExploreService
import javax.inject.Inject

class ExploreRDS @Inject constructor(private val exploreService: ExploreService) : BaseRDS(exploreService) {
    suspend fun getExplore(exploreRequestDTO: ExploreRequestDTO) = safeApiCall {
        exploreService.getExploreApi(exploreRequestDTO.culture)
    }

    suspend fun getExploreMap(exploreRequestDTO: ExploreRequestDTO) = safeApiCall {
        exploreService.getExploreMap(exploreRequestDTO.culture)
    }


}