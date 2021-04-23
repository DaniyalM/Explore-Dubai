package com.app.dubaiculture.data.repository.viewgallery.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImagesDTO(

    @SerializedName("Image")
    @Expose
    var img: String
)