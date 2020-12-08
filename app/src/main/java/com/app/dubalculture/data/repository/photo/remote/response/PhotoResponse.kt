package com.app.dubalculture.data.repository.photo.remote.response

import com.app.dubalculture.data.repository.base.BaseResponse


data class PhotoResponse constructor(
    val photo: PhotoDTO
) : BaseResponse()