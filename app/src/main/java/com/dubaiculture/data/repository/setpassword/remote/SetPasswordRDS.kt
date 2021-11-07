package com.dubaiculture.data.repository.setpassword.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequestDTO
import com.dubaiculture.data.repository.setpassword.remote.response.SetPasswordResponse
import com.dubaiculture.data.repository.setpassword.service.SetPasswordService
import javax.inject.Inject

class SetPasswordRDS @Inject constructor(private val setPasswordService: SetPasswordService) :
    BaseRDS() {
    suspend fun setPassword(setPasswordRequestDTO: SetPasswordRequestDTO): Result<SetPasswordResponse> {
        return safeApiCall {
            setPasswordService.setPassword(setPasswordRequestDTO)
        }
    }

}