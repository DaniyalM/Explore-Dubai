package com.dubaiculture.data.repository.viewgallery.remote

import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequestDTO
import com.dubaiculture.data.repository.viewgallery.service.ViewGalleryService
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