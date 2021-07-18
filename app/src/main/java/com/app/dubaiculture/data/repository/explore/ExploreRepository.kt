package com.app.dubaiculture.data.repository.explore

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.explore.local.models.AttractionsEvents
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.mapper.transformAttractionCategories
import com.app.dubaiculture.data.repository.explore.mapper.transformEvents
import com.app.dubaiculture.data.repository.explore.mapper.transformExplore
import com.app.dubaiculture.data.repository.explore.mapper.transformExploreRequest
import com.app.dubaiculture.data.repository.explore.remote.ExploreRDS
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.app.dubaiculture.utils.Constants.HTTP_RESPONSE.HTTP_200
import javax.inject.Inject


class ExploreRepository @Inject constructor(
    private val exploreRDS: ExploreRDS,
) : BaseRepository(exploreRDS) {
    suspend fun getExplore(exploreRequest: ExploreRequest): Result<List<Explore>> {
        return when (val resultRDS =
            exploreRDS.getExplore(transformExploreRequest(exploreRequest))) {
            is Result.Success -> {
                // Single Source Of Truth -> get data from server -> save to db -> get from db to provide to UI

                if (resultRDS.value.statusCode != HTTP_200) {
                    Result.Failure(true, resultRDS.value.statusCode, null)
                } else {
//                photoLDS.insertAll(listLDS as MutableList<Photo>)
//                val resultLDS = photoLDS.getAll()
                    Result.Success(transformExplore(resultRDS.value))
                }

            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS

        }


    }


    suspend fun getExploreMap(exploreRequest: ExploreRequest): Result<AttractionsEvents> {
        return when (val resultRDS =
            exploreRDS.getExploreMap(transformExploreRequest(exploreRequest))) {
            is Result.Success -> {
                if (resultRDS.value.statusCode != HTTP_200) {
                    Result.Failure(true, resultRDS.value.statusCode, null)
                } else {
                    Result.Success(
                        AttractionsEvents(
                            attractionCategory = transformAttractionCategories(resultRDS.value),
                            events = transformEvents(resultRDS.value)
                        )
                    )
                }

            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS

        }
    }
}
