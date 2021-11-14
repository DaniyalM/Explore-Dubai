package com.dubaiculture.data.repository.event.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FilterDTO (
    @SerializedName("ID")
    @Expose
    var id: String? = "",
    @SerializedName("Title")
    @Expose
    var title: String? = "",
        )


