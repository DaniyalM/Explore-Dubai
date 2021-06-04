package com.app.dubaiculture.data.repository.profile

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.mapper.transformAttractions
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.event.mapper.transformEventList
import com.app.dubaiculture.data.repository.profile.local.Favourite
import com.app.dubaiculture.data.repository.profile.local.ProfileLDS
import com.app.dubaiculture.data.repository.profile.remote.ProfileRDS
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.app.dubaiculture.data.repository.profile.utils.MultipartFormHelpers.getMultiPartData
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.data.repository.settings.mapper.transformUserSettingRequest
import com.app.dubaiculture.data.repository.settings.mapper.transformUserSettings
import com.app.dubaiculture.utils.Constants.Error.SOMETHING_WENT_WRONG
import com.app.dubaiculture.utils.event.Event
import javax.inject.Inject

class ProfileRepository @Inject constructor(
        private val profileRDS: ProfileRDS,
        private val profileLDS: ProfileLDS
) : BaseRepository(profileRDS) {

    suspend fun uploadProfilePicture(uri: String): Result<UploadProfileResponse> {
        val multipartData = getMultiPartData(uri)
                ?: return Result.Failure(true, null, null, "Some Thing Went Wrong")
        return when (val resultRDS =
                profileRDS.uploadProfilePicture(multipartData)) {
            is Result.Success -> {
                if (resultRDS.value.succeeded) {
                    Result.Success(resultRDS.value)
                } else {
                    Result.Failure(false, null, null, resultRDS.value.errorMessage)
                }
            }
            is Result.Error -> resultRDS
            is Result.Failure ->
                Result.Failure(true, null, null, SOMETHING_WENT_WRONG)

        }
    }

    suspend fun getSettings(): Result<Event<UserSettings>> = when (val resultRds = profileRDS.getSettings()) {
        is Result.Success -> {
            if (resultRds.value.succeeded) {
                Result.Success(Event(transformUserSettings(resultRds.value.result.userSettings)))
            } else {
                Result.Failure(false, null, null, resultRds.value.errorMessage)
            }
        }
        is Result.Error -> resultRds
        is Result.Failure -> resultRds
    }


    suspend fun updateSettings(userSettings: UserSettings) =
            when (val resultRDS = profileRDS.updateSettings(transformUserSettingRequest(userSettings))) {
                is Result.Success -> {
                    if (resultRDS.value.succeeded) {
                        Result.Success(Event(resultRDS.value.result.message))
                    } else {
                        Result.Failure(false, null, null, resultRDS.value.errorMessage)
                    }
                }
                is Result.Error -> resultRDS
                is Result.Failure -> resultRDS
            }


    suspend fun getFavourites() = when (val resultRDS = profileRDS.getFavourites()) {
        is Result.Success -> {
            if (resultRDS.value.succeeded) {
                Result.Success(Event(Favourite(
                        attractions = transformAttractions(resultRDS.value.result.attraction),
                        events = transformEventList(resultRDS.value.result.events)
                )))
            } else {
                Result.Failure(false, null, null, resultRDS.value.errorMessage)
            }
        }
        is Result.Error -> resultRDS
        is Result.Failure -> resultRDS
    }


}