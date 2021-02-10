package com.app.dubaiculture.data.repository.user.remote.response

import com.google.gson.annotations.SerializedName

class GuestTokenResponse(
    @SerializedName(value = "Result") val guestTokenResponseDTO: GuestTokenResponseDTO)