package com.app.dubaiculture.data.repository.explore.remote.response

import com.app.dubaiculture.data.repository.explore.local.models.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ExploreDTO(val category: String, val value:List<InnerValue>)

