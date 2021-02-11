package com.app.dubaiculture.data.repository.setpassword.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.registeration.remote.response.register.RegistrationResponse
import com.app.dubaiculture.data.repository.registeration.remote.service.RegistrationService
import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequestDTO
import com.app.dubaiculture.data.repository.setpassword.remote.response.SetPasswordResponse
import com.app.dubaiculture.data.repository.setpassword.remote.service.SetPasswordService
import com.app.neomads.data.repository.registration.remote.request.register.RegistrationRequestDTO
import javax.inject.Inject

class SetPasswordRDS @Inject constructor(private val setPasswordService: SetPasswordService) :
    BaseRDS() {
    suspend fun setPassword(setPasswordRequestDTO: SetPasswordRequestDTO): Result<SetPasswordResponse> {
        return safeApiCall {
            setPasswordService.setPassword(setPasswordRequestDTO)
        }
    }

}