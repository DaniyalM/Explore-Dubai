package com.daniyal.dubalculture.data.repository.photo.remote.response

import com.daniyal.dubalculture.data.repository.base.BaseResponse


data class PhotosResponse constructor(
    val photos: List<PhotoDTO>
) : BaseResponse()