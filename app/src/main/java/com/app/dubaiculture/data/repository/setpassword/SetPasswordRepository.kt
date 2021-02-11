package com.app.dubaiculture.data.repository.setpassword

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.registeration.remote.mapper.transform
import com.app.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponse
import com.app.dubaiculture.data.repository.setpassword.remote.SetPasswordRDS
import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequest
import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequestDTO
import com.app.dubaiculture.data.repository.setpassword.remote.response.SetPasswordResponse
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import retrofit2.http.POST
import javax.inject.Inject

class SetPasswordRepository @Inject constructor(private val setPasswordRDS: SetPasswordRDS)  : BaseRepository() {

    suspend fun setPassword(setPasswordRequest: SetPasswordRequest): Result<SetPasswordResponse> {
        return when (val resultRDS = setPasswordRDS.setPassword(transform(setPasswordRequest))) {
            is Result.Success -> {
                Result.Success(resultRDS.value)
            }
            is Result.Error -> {
                Result.Error(resultRDS.exception)
            }
            is Result.Failure -> {
                Result.Failure(resultRDS.isNetWorkError, resultRDS.errorCode, resultRDS.errorBody)
            }
        }
    }
}