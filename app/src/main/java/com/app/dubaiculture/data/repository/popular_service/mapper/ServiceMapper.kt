package com.app.dubaiculture.data.repository.popular_service.mapper

import com.app.dubaiculture.data.repository.popular_service.local.models.EService
import com.app.dubaiculture.data.repository.popular_service.local.models.EServices
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.app.dubaiculture.data.repository.popular_service.remote.response.EServiceDTO
import com.app.dubaiculture.data.repository.popular_service.remote.response.ServiceCategoryDTO
import com.app.dubaiculture.data.repository.popular_service.remote.response.ServiceResponse


fun transformService(serviceResponse: ServiceResponse): EServices =
    serviceResponse.Result.run {
        EServices(
            eServices = transformService(EServices),
            serviceCategory = transformServiceCategorys(ServiceCategory),
            title = Title,
            heading = Heading
        )
    }

fun transformService(eServices: List<EServiceDTO>): List<EService> =
    eServices.map {
        EService(
            category = it.Category,
            title = it.Title,
            summary = it.Summary,
            id = it.ID
        )
    }

fun transformServiceCategorys(serviceCategory: List<ServiceCategoryDTO>): List<ServiceCategory> =
    serviceCategory.map {
        ServiceCategory(
            title = it.Title,
            id = it.ID
        )
    }


