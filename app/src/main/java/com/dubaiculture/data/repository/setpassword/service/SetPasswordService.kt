package com.dubaiculture.data.repository.setpassword.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.setpassword.remote.request.SetPasswordRequestDTO
import com.dubaiculture.data.repository.setpassword.remote.response.SetPasswordResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface SetPasswordService :BaseService{
    @POST("Auth/SetPassword")
    suspend fun setPassword(@Body setPasswordRequestDTO: SetPasswordRequestDTO): SetPasswordResponse
}