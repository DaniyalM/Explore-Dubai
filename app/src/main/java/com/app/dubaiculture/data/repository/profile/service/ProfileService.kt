package com.app.dubaiculture.data.repository.profile.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.app.dubaiculture.data.repository.settings.remote.request.UserSettingsDTO
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileService : BaseService {
    @Multipart
    @POST("/api/Profile/UploadImage")
    suspend fun uploadAvatar(@Part Image: MultipartBody.Part): UploadProfileResponse

    @GET("/api/Profile/GetUserSettings")
    suspend fun getUserSettings(): UserSettingResponse

    @GET("Content/GetFavorites")
    suspend fun getfavourites(): UploadProfileResponse

    @POST("/api/Profile/UpdateSettings")
    suspend fun updateSettings(@Body settingsDTO: UserSettingsDTO): UploadProfileResponse
}