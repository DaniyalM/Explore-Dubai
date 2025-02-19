package com.dubaiculture.data.repository.viewgallery

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.viewgallery.local.ViewGalleryModel
import com.dubaiculture.data.repository.viewgallery.mapper.transformViewGallery
import com.dubaiculture.data.repository.viewgallery.mapper.transformViewGalleryRequest
import com.dubaiculture.data.repository.viewgallery.remote.ViewGalleryRDS
import com.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequest
import javax.inject.Inject

class ViewGalleryRepository @Inject constructor(private val viewGalleryRDS: ViewGalleryRDS) :
    BaseRepository(viewGalleryRDS) {
    suspend fun fetchMetaData(viewGalleryRequest: ViewGalleryRequest): Result<ViewGalleryModel> {
        return when (val resultRds =
            viewGalleryRDS.getMetaDataAr(transformViewGalleryRequest(viewGalleryRequest))) {
            is Result.Success -> {
                val viewGalleryLDS = resultRds
                if (viewGalleryLDS.value.statusCode != 200) {
                    Result.Failure(true, viewGalleryLDS.value.statusCode, null)
                } else {
                    val siteMapResponse = transformViewGallery(viewGalleryLDS.value)
                    Result.Success(siteMapResponse)
                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }
    }