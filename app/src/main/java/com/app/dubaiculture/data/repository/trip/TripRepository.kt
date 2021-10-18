package com.app.dubaiculture.data.repository.trip

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.trip.local.UserTypes
import com.app.dubaiculture.data.repository.trip.mapper.transformUserType
import com.app.dubaiculture.data.repository.trip.remote.TripRDS
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.security.rds.SecurityManagerRDS
import javax.inject.Inject

class TripRepository @Inject constructor(
    private val tripRDS: TripRDS,
    private val securityManager: SecurityManagerRDS
) :
    BaseRepository() {

    suspend fun getUserType(): Result<Event<UserTypes>> =
        when (val resultRds = tripRDS.getUserType()) {
            is Result.Success -> {
                if (resultRds.value.succeeded) {
                    Result.Success(Event(transformUserType(resultRds.value.userTypeResponseDTO)))
                } else {
                    Result.Failure(false, null, null, resultRds.value.errorMessage)
                }
            }
            is Result.Error -> resultRds
            is Result.Failure -> resultRds
        }

}