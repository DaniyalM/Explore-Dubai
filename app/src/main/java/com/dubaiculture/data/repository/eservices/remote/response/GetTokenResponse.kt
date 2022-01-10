package com.dubaiculture.data.repository.eservices.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.base.EServiceBaseResponse
import com.dubaiculture.data.repository.trip.remote.response.EventAttractionResponseDTO
import com.google.gson.annotations.SerializedName

class GetTokenResponse constructor(@SerializedName(value = "data") val getTokenResponseDTO: GetTokenResponseDTO) :
    EServiceBaseResponse()