package com.app.dubaiculture.data.repository.base

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.mapper.transformAddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse


abstract class BaseRepository {

    suspend fun addToFavourite(
        addToFavouriteRequest: AddToFavouriteRequest,
        baseRDS: BaseRDS,
    ): Result<AddToFavouriteResponse> {
        return when (val resultRds =
            baseRDS.addToFavourates(transformAddToFavouriteRequest(addToFavouriteRequest))) {
            is Result.Success -> {
                val attractionLDS = resultRds
                if (attractionLDS.value?.statusCode != 200) {
                    Result.Failure(true, attractionLDS.value?.statusCode, null)
                } else {
                    val eventRds = attractionLDS.value
                    Result.Success(eventRds)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds

        }
    }


}