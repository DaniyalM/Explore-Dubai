package com.app.dubaiculture.data.repository.profile.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequestDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionResponse
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.app.dubaiculture.data.repository.profile.service.ProfileService
import com.app.dubaiculture.data.repository.settings.remote.request.UserSettingsDTO
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRDS @Inject constructor(private val profileService: ProfileService) : BaseRDS() {
    suspend fun uploadProfilePicture(image: MultipartBody.Part): Result<UploadProfileResponse>
    = safeApiCall {
        profileService.uploadAvatar(image)
    }

    suspend fun getSettings(): Result<UserSettingResponse> = safeApiCall {
        profileService.getUserSettings()
    }


    suspend fun updateSettings(userSettingsDTO: UserSettingsDTO)=safeApiCall {
        profileService.updateSettings(userSettingsDTO)
    }
}