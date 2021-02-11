package com.app.dubaiculture.data.repository.setpassword

import com.app.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequestDTO
import com.app.dubaiculture.data.repository.setpassword.remote.response.SetPasswordResponse
import retrofit2.http.POST

interface SetPasswordRepository {
    @POST("Auth/SetPassword")
    suspend fun setPassword(setPasswordRequestDTO: SetPasswordRequestDTO): SetPasswordResponse
}