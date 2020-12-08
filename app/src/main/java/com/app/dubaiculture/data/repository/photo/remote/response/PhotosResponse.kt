package com.app.dubaiculture.data.repository.photo.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse


data class PhotosResponse constructor(
    val photos: List<PhotoDTO>
) : BaseResponse()