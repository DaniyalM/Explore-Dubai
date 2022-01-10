package com.dubaiculture.data.repository.forgot

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.forgot.mapper.transform
import com.dubaiculture.data.repository.forgot.remote.ForgotRDS
import com.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import javax.inject.Inject

class ForgotRepository @Inject constructor(private val forgotRDS: ForgotRDS):
    BaseRepository() {
    suspend fun forgot(forgotRequest: ForgotRequest): Result<ForgotResponse> {
        return when(val resultRDS = forgotRDS.forgotEmail(transform(forgotRequest))){
            is Result.Success->{
                Result.Success(resultRDS.value)
            }
            is Result.Error->resultRDS
            is Result.Failure ->resultRDS
        }
    }
}