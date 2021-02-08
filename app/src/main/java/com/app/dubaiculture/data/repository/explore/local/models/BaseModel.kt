package com.app.dubaiculture.data.repository.explore.local.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName





data class BaseModel (
    @SerializedName("ID")
    @Expose
    var id: String? = null,

    @SerializedName("Icon")
    @Expose
    var icon: String? = null,

    @SerializedName("Title")
    @Expose
    var title: String? = null,

    @SerializedName("Category")
    @Expose
    var category: String? = null,

    @SerializedName("LocationTitle")
    @Expose
    var locationTitle: String? = "",

    @SerializedName("Location")
    @Expose
    var location: String? = null,

    @SerializedName("PortraitImage")
    @Expose
    var portraitImage: String? = null,

    @SerializedName("LandscapeImage")
    @Expose
    var landscapeImage: String? = null,

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

    @SerializedName("PostedDate")
    @Expose
    var postedDate: String? = null,

    @SerializedName("Date")
    @Expose
    var date: String? = null
)