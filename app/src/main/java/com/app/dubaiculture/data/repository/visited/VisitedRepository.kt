package com.app.dubaiculture.data.repository.visited

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.visited.mapper.transformVisitedMapper
import com.app.dubaiculture.data.repository.visited.remote.VisitedRDS
import com.app.dubaiculture.data.repository.visited.remote.request.AddVisitedPlaceRequest
import com.app.dubaiculture.utils.Constants
import javax.inject.Inject

class VisitedRepository @Inject constructor(private val visitedRDS: VisitedRDS) :
    BaseRepository(visitedRDS) {


    suspend fun addVisitedPlace(addVisitedPlaceRequest: AddVisitedPlaceRequest): Result<String> {
        return when(val result=visitedRDS.addVisitedPlaces(transformVisitedMapper(addVisitedPlaceRequest))){
            is Result.Success -> {
                if (result.value.succeeded){
                    Result.Success(result.value.result.message)
                }else {
                    Result.Failure(true, null, null,
                        result.value.errorMessage
                    )
                }
            }
            is Result.Failure -> Result.Failure(true, null, null,
                Constants.Error.SOMETHING_WENT_WRONG
            )
            is Result.Error -> Result.Failure(true, null, null,
                Constants.Error.SOMETHING_WENT_WRONG
            )

        }
    }
}