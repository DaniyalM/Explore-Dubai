package com.dubaiculture.data.repository.profile.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.dubaiculture.data.repository.profile.service.ProfileService
import com.dubaiculture.data.repository.settings.remote.request.UserSettingsDTO
import com.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRDS @Inject constructor(private val profileService: ProfileService) : BaseRDS(profileService) {
    suspend fun uploadProfilePicture(image: MultipartBody.Part): Result<UploadProfileResponse> = safeApiCall {
        profileService.uploadAvatar(image)
    }

    suspend fun getSettings(): Result<UserSettingResponse> = safeApiCall {
        profileService.getUserSettings()
    }


    suspend fun updateSettings(userSettingsDTO: UserSettingsDTO) = safeApiCall {
        profileService.updateSettings(userSettingsDTO)
    }

    suspend fun getFavourites() = safeApiCall {
        profileService.getfavourites()
    }
}