package com.app.dubaiculture.data.repository.base

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.mapper.transformAddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.data.repository.event.remote.response.AddToFavouriteResponse


abstract class BaseRepository(private val baseRDS: BaseRDS?=null) {

    suspend fun addToFavourite(
        addToFavouriteRequest: AddToFavouriteRequest,
        ): Result<AddToFavouriteResponse> {
        return when (val resultRds =
            baseRDS!!.addToFavourates(transformAddToFavouriteRequest(addToFavouriteRequest))) {
            is Result.Success -> {
                if (resultRds.value!!.statusCode != 200) {
                    Result.Failure(true, resultRds.value.statusCode, null)
                } else {
                    val eventRds = resultRds.value
                    Result.Success(eventRds)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds

        }
    }
}