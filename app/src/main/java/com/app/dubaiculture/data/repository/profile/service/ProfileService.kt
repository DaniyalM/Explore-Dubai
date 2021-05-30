package com.app.dubaiculture.data.repository.profile.service

import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileService : BaseService {

    @POST("/api/Profile/UploadImage")
    suspend fun uploadAvatar(@Part Image: MultipartBody.Part): UploadProfileResponse
}