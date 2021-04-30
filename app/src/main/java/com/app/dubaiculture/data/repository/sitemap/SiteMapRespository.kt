package com.app.dubaiculture.data.repository.sitemap

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.mapper.transformEventFiltersRequest
import com.app.dubaiculture.data.repository.event.mapper.transformOtherEvents
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.sitemap.local.AttractionsSiteMap
import com.app.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.app.dubaiculture.data.repository.sitemap.mapper.transformSiteMap
import com.app.dubaiculture.data.repository.sitemap.mapper.trasformSiteMapRequest
import com.app.dubaiculture.data.repository.sitemap.remote.SiteMapRDS
import com.app.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequest
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
                    val siteMapResponse  = transformSiteMap(sitemapLDS.value)
                    Result.Success( siteMapResponse)

                }
            }
            is Result.Failure -> resultRds
            is Result.Error -> resultRds
        }
    }

}