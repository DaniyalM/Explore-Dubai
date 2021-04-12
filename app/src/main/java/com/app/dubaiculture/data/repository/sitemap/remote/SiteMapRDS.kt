package com.app.dubaiculture.data.repository.sitemap.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequestDTO
import com.app.dubaiculture.data.repository.sitemap.service.SiteMapService
import javax.inject.Inject

class SiteMapRDS @Inject constructor(private val siteMapService: SiteMapService) :
    BaseRDS() {

    suspend fun getSiteMap(siteMapRequestDTO: SiteMapRequestDTO) =
        safeApiCall {
            siteMapService.siteMap(siteMapRequestDTO.id!!, siteMapRequestDTO.culture)
        }
}