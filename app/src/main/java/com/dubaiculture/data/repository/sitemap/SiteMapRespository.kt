package com.dubaiculture.data.repository.sitemap

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRepository
import com.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.dubaiculture.data.repository.sitemap.mapper.transformSiteMap
import com.dubaiculture.data.repository.sitemap.mapper.trasformSiteMapRequest
import com.dubaiculture.data.repository.sitemap.remote.SiteMapRDS
import com.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequest
import javax.inject.Inject

class SiteMapRespository @Inject constructor(private val siteMapRDS: SiteMapRDS) :
    BaseRepository(siteMapRDS) {
    suspend fun fetchSiteMap(siteMapRequest: SiteMapRequest): Result<SiteMapModel> {
        return when (val resultRds =
            siteMapRDS.getSiteMap(trasformSiteMapRequest(siteMapRequest))) {
            is Result.Success -> {
                val sitemapLDS = resultRds
                if (sitemapLDS.value.statusCode != 200) {
                    Result.Failure(true, sitemapLDS.value.statusCode, null)
                } else {
                    val siteMapResponse = transformSiteMap(sitemapLDS.value)
                    Result.Success(siteMapResponse)

                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

}