package com.daniyal.dubalculture.data.repository.photo.remote.response

import com.daniyal.dubalculture.data.repository.base.BaseResponse


data class PhotoResponse constructor(
    val photo: PhotoDTO
) : BaseResponse()