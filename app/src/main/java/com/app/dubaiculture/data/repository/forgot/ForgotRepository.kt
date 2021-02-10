package com.app.dubaiculture.data.repository.forgot

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.forgot.mapper.transform
import com.app.dubaiculture.data.repository.forgot.remote.ForgotRDS
import com.app.dubaiculture.data.repository.forgot.remote.request.ForgotRequest
import com.app.dubaiculture.data.repository.forgot.remote.response.ForgotResponse
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import javax.inject.Inject

class ForgotRepository @Inject constructor(private val forgotRDS: ForgotRDS): BaseRepository() {
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