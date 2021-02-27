package com.app.dubaiculture.data.repository.explore

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.mapper.transformExplore
import com.app.dubaiculture.data.repository.explore.mapper.transformExploreRequest
import com.app.dubaiculture.data.repository.explore.remote.ExploreRDS
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject


class ExploreRepository @Inject constructor(
    private val exploreRDS: ExploreRDS
) : BaseRepository()  {
    suspend fun getExplore(exploreRequest: ExploreRequest): Result<List<Explore>> {
        return when (val resultRDS = exploreRDS.getExplore(transformExploreRequest(exploreRequest))) {
            is Result.Success -> {
                // Single Source Of Truth -> get data from server -> save to db -> get from db to provide to UI
                val listRDS = resultRDS
                if (listRDS.value.statusCode != 200) {
                    Result.Failure(true,listRDS.value.statusCode,null)
                }else{
                    val listLDS = listRDS.value.Result.value?.let { transformExplore(it) }!!
//                photoLDS.insertAll(listLDS as MutableList<Photo>)
//                val resultLDS = photoLDS.getAll()
                    Result.Success(listLDS)
                }

            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS

        }


    }
}