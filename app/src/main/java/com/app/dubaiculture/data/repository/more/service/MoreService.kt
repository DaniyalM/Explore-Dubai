package com.app.dubaiculture.data.repository.more.service

import com.app.dubaiculture.data.repository.base.BaseService
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.app.dubaiculture.data.repository.settings.remote.request.UserSettingsDTO
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface MoreService: BaseService {

    @GET("/api/Profile/GetUserSettings")
    suspend fun getUserSettings(): UserSettingResponse

}