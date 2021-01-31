package com.app.dubaiculture.data.repository.explore

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.explore.local.ExploreLDS
import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.app.dubaiculture.data.repository.explore.mapper.transform
import com.app.dubaiculture.data.repository.explore.remote.ExploreRDS
import javax.inject.Inject

class ExploreRepository @Inject constructor(
    val exploreLDS: ExploreLDS,
    val exploreRDS: ExploreRDS
) {
    suspend fun getPhotos(): Result<List<UpComingEvents>> {
        return when (val resultRDS = exploreRDS.getExplore()) {
            is Result.Success -> {
                // Single Source Of Truth -> get data from server -> save to db -> get from db to provide to UI
                val listRDS = resultRDS.data
                val listLDS = transform(listRDS.data)
//                photoLDS.insertAll(listLDS as MutableList<Photo>)
//                val resultLDS = photoLDS.getAll()
                Result.Success(listLDS)
            }
            is Result.Error -> {
                resultRDS
            }
            is Result.Failure -> {
                resultRDS
            }
        }


    }
}