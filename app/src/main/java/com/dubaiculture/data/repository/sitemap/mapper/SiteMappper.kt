package com.dubaiculture.data.repository.sitemap.mapper

import com.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequest
import com.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequestDTO
import com.dubaiculture.data.repository.sitemap.remote.response.IBeaconsItemsDTO
import com.dubaiculture.data.repository.sitemap.remote.response.SiteMapAttractionDTO
import com.dubaiculture.data.repository.sitemap.remote.response.SiteMapResponse

fun trasformSiteMapRequest(siteMapRequest : SiteMapRequest) =
    SiteMapRequestDTO(
        id = siteMapRequest.id,
        culture = siteMapRequest.culture!!
    )
fun transformSiteMap(siteMapResponse: SiteMapResponse): SiteMapModel =
    siteMapResponse.Result.attractions.run {
        transformSiteMapObject(this)
    }

fun transformSiteMapObject(siteMapAttractionDTO: SiteMapAttractionDTO): SiteMapModel = SiteMapModel(

    attractionID = siteMapAttractionDTO.id,
    ibeconImg = siteMapAttractionDTO.iBeacon.img,
    ibeconItems = siteMapAttractionDTO.iBeacon.iBeaconsItems.mapIndexed { index, iBeaconsItemsDTO ->
        transformIbeconList(iBeaconsItemsDTO, index)
    }
)

fun transformIbeconList(iBeaconsItemsDTO: IBeaconsItemsDTO, index: Int) = BeaconItems(
    step = iBeaconsItemsDTO.step,
    title = iBeaconsItemsDTO.title,
    subtitle  = iBeaconsItemsDTO.subtitle,
    image = iBeaconsItemsDTO.img,
    thumbnail = iBeaconsItemsDTO.thumbnail,
    summary = iBeaconsItemsDTO.summary,
    deviceID = iBeaconsItemsDTO.deviceID,
    visited = iBeaconsItemsDTO.visited ?: false,
    visitedOn = iBeaconsItemsDTO.visitedOn ?: "",
    proximityID = iBeaconsItemsDTO.proximityID ?: "",
    id = index + 1,
    minor = iBeaconsItemsDTO.minor?:"",
    major = iBeaconsItemsDTO.major?:"",
    serial = iBeaconsItemsDTO.serial?:"",
    itemId = iBeaconsItemsDTO.ItemId?:""
)



