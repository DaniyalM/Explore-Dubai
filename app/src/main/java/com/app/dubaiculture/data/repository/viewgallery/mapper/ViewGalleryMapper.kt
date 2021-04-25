package com.app.dubaiculture.data.repository.viewgallery.mapper

import com.app.dubaiculture.data.repository.viewgallery.local.Images
import com.app.dubaiculture.data.repository.viewgallery.local.ViewGalleryModel
import com.app.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequest
import com.app.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequestDTO
import com.app.dubaiculture.data.repository.viewgallery.remote.response.ImagesDTO
import com.app.dubaiculture.data.repository.viewgallery.remote.response.ViewGalleryDTO
import com.app.dubaiculture.data.repository.viewgallery.remote.response.ViewGalleryResponse

fun transformViewGalleryRequest(viewGalleryRequest: ViewGalleryRequest) =
    ViewGalleryRequestDTO(
        id = viewGalleryRequest.id,
        culture = viewGalleryRequest.culture!!
    )

fun transformViewGallery(viewGalleryResponse: ViewGalleryResponse): ViewGalleryModel =
    viewGalleryResponse.Result.viewGallery.run {
        transformationViewGalleryObject(this)
    }

fun transformationViewGalleryObject(viewGalleryDTO: ViewGalleryDTO): ViewGalleryModel =
    ViewGalleryModel(
        id = viewGalleryDTO.id,
        title = viewGalleryDTO.title,
        desc = viewGalleryDTO.desc,
        images = viewGalleryDTO.images?.map {
            transformationViewGalleryList(it)
        }

    )

fun transformationViewGalleryList(imagesDTO: ImagesDTO): Images = Images(
    image = imagesDTO.img

)