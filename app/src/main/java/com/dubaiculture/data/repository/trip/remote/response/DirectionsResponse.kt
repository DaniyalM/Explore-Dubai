package com.dubaiculture.data.repository.trip.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DirectionResponse(
    val routes:List<Route>,
    val status:String
)

data class Route(
    @SerializedName("overview_polyline")
    @Expose
    val overviewPolyline: OverviewPolyline
)

data class OverviewPolyline(
    val points:String
)