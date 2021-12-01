package com.dubaiculture.data.repository.eservices.remote.response

import com.dubaiculture.data.repository.base.EServiceBaseResponse

data class FormResponse(val data: FormInnerResponse): EServiceBaseResponse()
data class FormInnerResponse(val SerialNo: String)