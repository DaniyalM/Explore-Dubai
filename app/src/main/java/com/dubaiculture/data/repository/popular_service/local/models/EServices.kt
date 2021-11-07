package com.dubaiculture.data.repository.popular_service.local.models

data class EServices(
    val eServices: List<EService>,
    val heading: String,
    val serviceCategory: List<ServiceCategory>,
    val title: String
)
