package com.app.dubalculture.data.repository.photo.remote.response

import com.app.dubalculture.data.repository.base.BaseResponse


data class PhotosResponse constructor(
    val photos: List<PhotoDTO>
) : BaseResponse()