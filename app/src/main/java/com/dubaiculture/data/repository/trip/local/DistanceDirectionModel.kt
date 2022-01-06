package com.dubaiculture.data.repository.trip.local

import com.dubaiculture.data.repository.trip.remote.response.DirectionResponse
import com.dubaiculture.data.repository.trip.remote.response.DistanceMatrixResponse

data class DistanceDirectionModel(
    val directionResponse: DirectionResponse,
    val distanceResponse: DistanceMatrixResponse
)