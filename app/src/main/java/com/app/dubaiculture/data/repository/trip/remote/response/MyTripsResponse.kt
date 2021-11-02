package com.app.dubaiculture.data.repository.trip.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.google.gson.annotations.SerializedName

class MyTripsResponse constructor(@SerializedName(value = "Result") val myTripResponseDTO: MyTripResponseDTO) :
    BaseResponse()