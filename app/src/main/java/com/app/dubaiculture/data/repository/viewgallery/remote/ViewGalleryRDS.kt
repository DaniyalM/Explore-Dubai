package com.app.dubaiculture.data.repository.viewgallery.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequestDTO
import com.app.dubaiculture.data.repository.viewgallery.service.ViewGalleryService
import javax.inject.Inject

class ViewGalleryRDS @Inject constructor(private val viewGalleryService: ViewGalleryService) :
    BaseRDS() {

    suspend fun getMetaDataAr(viewGalleryRequestDTO: ViewGalleryRequestDTO) =
        safeApiCall {
            viewGalleryService.getMetaData(
                viewGalleryRequestDTO.id!!,
                viewGalleryRequestDTO.culture
            )
        }
}