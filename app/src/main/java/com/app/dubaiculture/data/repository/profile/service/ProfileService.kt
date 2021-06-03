package com.app.dubaiculture.data.repository.profile.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileService : BaseService {
    @Multipart
    @POST("/api/Profile/UploadImage")
    suspend fun uploadAvatar(@Part Image: MultipartBody.Part): UploadProfileResponse

    @GET("/api/Profile/GetUserSettings")
    suspend fun getUserSettings(): UserSettingResponse

//    @POST("/api/Profile/GetUserSettings")
//    suspend fun getSettings(@Body settingsDTO: UserSettingsDTO): UploadProfileResponse
}