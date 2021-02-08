package com.app.dubaiculture.data.repository.explore

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.explore.local.ExploreLDS
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.mapper.transformExplore
import com.app.dubaiculture.data.repository.explore.remote.ExploreRDS
import javax.inject.Inject

class ExploreRepository @Inject constructor(
    private val exploreRDS: ExploreRDS
) : BaseRepository()  {
    suspend fun getExplore(): Result<List<Explore>> {
        return when (val resultRDS = exploreRDS.getExplore()) {
            is Result.Success -> {
                // Single Source Of Truth -> get data from server -> save to db -> get from db to provide to UI
                val listRDS = resultRDS
                val listLDS = transformExplore(listRDS.value.Result.value)
//                photoLDS.insertAll(listLDS as MutableList<Photo>)
//                val resultLDS = photoLDS.getAll()
                Result.Success(listLDS)
            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS

        }


    }
}