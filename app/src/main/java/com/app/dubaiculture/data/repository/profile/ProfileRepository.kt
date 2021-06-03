package com.app.dubaiculture.data.repository.profile

import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.profile.remote.ProfileRDS
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.profile.local.ProfileLDS
import com.app.dubaiculture.data.repository.profile.remote.response.UploadProfileResponse
import com.app.dubaiculture.data.repository.profile.utils.MultipartFormHelpers.getMultiPartData
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.data.repository.settings.mapper.transformUserSettings
import com.app.dubaiculture.data.repository.settings.remote.response.UserSettingResponse
import com.app.dubaiculture.utils.Constants.Error.SOMETHING_WENT_WRONG
import javax.inject.Inject
import com.app.dubaiculture.utils.event.Event

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

    suspend fun getSettings():Result<Event<UserSettings>> = when(val resultRds=profileRDS.getSettings()){
        is Result.Success -> {
            if(resultRds.value.succeeded){
                Result.Success(Event(transformUserSettings(resultRds.value.result.userSettings)))
            }else{
                Result.Failure(false,null,null,resultRds.value.errorMessage)
            }
        }
        is Result.Error -> resultRds
        is Result.Failure -> resultRds
    }




}