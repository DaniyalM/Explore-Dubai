package com.app.dubaiculture.data.repository.sitemap.mapper

import com.app.dubaiculture.data.repository.event.remote.request.EventFiltersRequestDTO
import com.app.dubaiculture.data.repository.sitemap.local.AttractionsSiteMap
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.app.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequest
import com.app.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequestDTO
import com.app.dubaiculture.data.repository.sitemap.remote.response.IBeaconsItemsDTO
import com.app.dubaiculture.data.repository.sitemap.remote.response.IbeconDTO
import com.app.dubaiculture.data.repository.sitemap.remote.response.SiteMapAttractionDTO
import com.app.dubaiculture.data.repository.sitemap.remote.response.SiteMapResponse

fun trasformSiteMapRequest(siteMapRequest : SiteMapRequest) =
    SiteMapRequestDTO(
        id = siteMapRequest.id,
        culture = siteMapRequest.culture!!
    )
fun transformSiteMap(siteMapResponse: SiteMapResponse): SiteMapModel =
    siteMapResponse.Result.attractions.run {
        transformSiteMapObject(this)
    }
fun transformSiteMapObject(siteMapAttractionDTO: SiteMapAttractionDTO):SiteMapModel = SiteMapModel(
    attractionID = siteMapAttractionDTO.id,
    ibeconImg = siteMapAttractionDTO.iBeacon.img,
    ibeconItems = siteMapAttractionDTO.iBeacon.iBeaconsItems!!.map {
        transformIbeconList(it)
    } as ArrayList<IbeconITemsSiteMap>
)
fun transformIbeconList(iBeaconsItemsDTO : IBeaconsItemsDTO) : IbeconITemsSiteMap = IbeconITemsSiteMap(
    step = iBeaconsItemsDTO.step,
    title = iBeaconsItemsDTO.title,
    thumbnail = iBeaconsItemsDTO.thumbnail,
    summary = iBeaconsItemsDTO.summary,
    deviceID = iBeaconsItemsDTO.deviceID,
    )



