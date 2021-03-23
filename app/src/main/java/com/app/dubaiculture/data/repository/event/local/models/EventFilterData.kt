package com.app.dubaiculture.data.repository.event.local.models

import com.app.dubaiculture.data.repository.event.remote.response.FilterDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EventFilterData (
    var radioGroupList: ArrayList<Filter>?=null,
    var categoryList: ArrayList<Filter>?=null,
    var locationList: ArrayList<Filter>?=null,
        )