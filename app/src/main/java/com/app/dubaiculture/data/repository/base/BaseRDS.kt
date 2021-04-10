package com.app.dubaiculture.data.repository.base

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequestDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseRDS(private val baseService: BaseService?=null) {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Result.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Result.Failure(true, null, null)
                    }
                }
            }

        }
    }
    suspend fun addToFavourates(addToFavouriteRequestDTO: AddToFavouriteRequestDTO) =
        safeApiCall {
            baseService?.addToFavourites(addToFavouriteRequestDTO)
        }



}