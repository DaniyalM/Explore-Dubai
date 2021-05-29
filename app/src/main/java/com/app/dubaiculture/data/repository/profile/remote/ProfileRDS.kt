package com.app.dubaiculture.data.repository.profile.remote

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.app.dubaiculture.data.repository.profile.service.ProfileService
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRDS @Inject constructor(val profileService: ProfileService) : BaseRDS() {
    suspend fun uploadProfilePicture(image: MultipartBody.Part): Result<UploadProfileResponse> {
        return safeApiCall {
            profileService.uploadAvatar(image)
        }
    }
}