package com.app.dubaiculture.data.repository.explore.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse

data class ExploreResponse constructor(
    val data: List<ExploreDTO>
) : BaseResponse()
