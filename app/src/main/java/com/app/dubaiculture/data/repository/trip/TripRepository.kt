package com.app.dubaiculture.data.repository.trip

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.login.remote.LoginRDS
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.data.repository.settings.mapper.transformUserSettings
import com.app.dubaiculture.data.repository.trip.mapper.transform
import com.app.dubaiculture.data.repository.trip.remote.TripRDS
import com.app.dubaiculture.data.repository.trip.remote.response.UserTypeResponseDTO
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.security.rds.SecurityManagerRDS
import javax.inject.Inject

class TripRepository @Inject constructor(private val tripRDS: TripRDS, private val securityManager: SecurityManagerRDS):
    BaseRepository()  {

    suspend fun getUserType(): Result<Event<UserTypeResponseDTO>> = when (val resultRds = tripRDS.getUserType()) {
        is Result.Success -> {
            if (resultRds.value.succeeded) {
                Result.Success(Event(transform(resultRds.value)))
            } else {
                Result.Failure(false, null, null, resultRds.value.errorMessage)
            }
        }
        is Result.Error -> resultRds
        is Result.Failure -> resultRds
    }

}