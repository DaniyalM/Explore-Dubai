package com.app.dubaiculture.data.repository.explore.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequestDTO
import com.app.dubaiculture.data.repository.explore.service.ExploreService
import javax.inject.Inject

class ExploreRDS @Inject constructor(private val exploreService: ExploreService) : BaseRDS(exploreService) {
//    suspend fun getExplore(culture : String) = safeApiCall {
//        exploreService.getExploreApi(culture)
//    }

    suspend fun getExplore(exploreRequestDTO: ExploreRequestDTO) = safeApiCall {
        exploreService.getExploreApi(exploreRequestDTO.culture)
    }
}