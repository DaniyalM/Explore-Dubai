package com.app.dubaiculture.data.repository.photo.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse


data class PhotoResponse constructor(
    val photo: PhotoDTO
) : BaseResponse()