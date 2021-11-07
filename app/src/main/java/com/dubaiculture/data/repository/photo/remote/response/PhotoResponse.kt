package com.dubaiculture.data.repository.photo.remote.response

import com.dubaiculture.data.repository.base.BaseResponse


data class PhotoResponse constructor(
    val photo: PhotoDTO
) : BaseResponse()