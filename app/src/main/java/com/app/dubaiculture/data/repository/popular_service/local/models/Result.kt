package com.app.dubaiculture.data.repository.popular_service.local.models

import com.app.dubaiculture.data.repository.popular_service.remote.response.EServiceDTO
import com.app.dubaiculture.data.repository.popular_service.remote.response.ServiceCategoryDTO

data class Result(
    val EServices: List<EServiceDTO>,
    val Heading: String,
    val ServiceCategory: List<ServiceCategoryDTO>,
    val Title: String
)