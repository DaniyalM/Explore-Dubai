package com.dubaiculture.data.repository.visited.mapper

import com.dubaiculture.data.repository.visited.remote.request.AddVisitedPlaceRequest
import com.dubaiculture.data.repository.visited.remote.request.AddVisitedPlacedRequestDTO


fun transformVisitedMapper(addVisitedPlaceRequest: AddVisitedPlaceRequest) =
    AddVisitedPlacedRequestDTO(
        ItemID = addVisitedPlaceRequest.itemId,
        DeviceID = addVisitedPlaceRequest.deviceId,
        AddedOn = addVisitedPlaceRequest.addedOn,
        Type = addVisitedPlaceRequest.type
    )