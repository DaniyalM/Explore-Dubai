package com.dubaiculture.data.repository.eservices.remote.response

import com.dubaiculture.data.repository.base.EServiceBaseResponse
import com.google.gson.annotations.SerializedName

class GetFieldValueResponse constructor(@SerializedName(value = "data") val getFieldValueResponseDTO: List<GetFieldValueResponseDTOItem>) :
    EServiceBaseResponse()