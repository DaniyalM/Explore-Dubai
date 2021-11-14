package com.dubaiculture.data.repository.photo.mapper

import com.dubaiculture.data.repository.photo.local.Photo
import com.dubaiculture.data.repository.photo.remote.response.PhotoDTO
import com.dubaiculture.data.repository.photo.remote.response.PhotoResponse
import com.dubaiculture.data.repository.photo.remote.response.PhotosResponse

fun transform(photoResponse: PhotoResponse): Photo =
    photoResponse.photo.run {
        transform(this)
    }

fun transform(photosResponse: PhotosResponse): List<Photo> =
    photosResponse.photos.run {
        transform(this)
    }

fun transform(photoDTO: PhotoDTO): Photo = photoDTO.run {
    Photo(id = id!!, likes = likes!!, desc = desc!!)
}

fun transform(list: List<PhotoDTO>): List<Photo> = list.run {
    this.map { Photo(id = it.id!!, likes = it.likes!!, desc = it.desc!!) }
}
