package com.app.dubalculture.data.repository.base

import com.app.dubalculture.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRDS {
    suspend fun <T> safeApiCall(
        apiCall:suspend ()->T
    ):Result<T>{
        return withContext(Dispatchers.IO){
            try {
                Result.Success(apiCall.invoke())
            }catch (throwable:Throwable){
                when(throwable){
                    is HttpException -> {
                        Result.Failure(false,throwable.code(),throwable.response()?.errorBody())
                    }
                    else->{
                        Result.Failure(true,null,null)
                    }
                }
            }

        }
    }


}