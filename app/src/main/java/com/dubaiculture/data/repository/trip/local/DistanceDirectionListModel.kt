package com.dubaiculture.data.repository.trip.local

import com.dubaiculture.data.repository.trip.remote.response.DirectionResponse
import com.dubaiculture.data.repository.trip.remote.response.DistanceMatrixResponse

data class DistanceDirectionListModel(
    val directionResponse: List<DirectionResponse>,
    val distanceResponse: List<DistanceMatrixResponse>
)