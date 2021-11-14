package com.dubaiculture.data.repository.popular_service.remote.response

data class ProcedureDTO(
    val ServiceProcedure: List<ServiceProcedureDTO>?= mutableListOf(),
    val ServiceProcedureTitle: String?
)