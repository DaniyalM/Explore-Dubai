package com.app.dubaiculture.data.repository.explore.local.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpComingEvents(
    @SerializedName("ID")
    @Expose
    var id: String? = null,

    @SerializedName("Title")
    @Expose
    var title: String? = null,

    @SerializedName("Category")
    @Expose
    var category: String? = null,

    @SerializedName("Image")
    @Expose
    var image: String? = null,

    @SerializedName("FromDate")
    @Expose
    var fromDate: String? = null,

    @SerializedName("FromMonthYear")
    @Expose
    var fromMonthYear: String? = null,

    @SerializedName("FromTime")
    @Expose
    var fromTime: String? = null,

    @SerializedName("FromDay")
    @Expose
    var fromDay: String? = null,

    @SerializedName("ToDate")
    @Expose
    var toDate: String? = null,

    @SerializedName("ToMonthYear")
    @Expose
    var toMonthYear: String? = null,

    @SerializedName("ToTime")
    @Expose
    var toTime: String? = null,

    @SerializedName("ToDay")
    @Expose
    var toDay: String? = null,

    @SerializedName("Type")
    @Expose
    var type: String? = null,


    )





