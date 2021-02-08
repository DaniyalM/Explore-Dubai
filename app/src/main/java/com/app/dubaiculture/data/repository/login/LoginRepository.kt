package com.app.dubaiculture.data.repository.login

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.login.remote.LoginRDS
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.response.LoginResponse
import com.app.dubaiculture.data.repository.registeration.mapper.transform
import com.app.dubaiculture.data.repository.registeration.remote.mapper.transform
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginRDS: LoginRDS):BaseRepository() {
        suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
            return when(val resultRDS = loginRDS.login(transform(loginRequest))){
                    is Result.Success->{
                        Result.Success(resultRDS.value)
                    }
                    is Result.Error->resultRDS
                    is Result.Failure ->resultRDS
            }
        }
}