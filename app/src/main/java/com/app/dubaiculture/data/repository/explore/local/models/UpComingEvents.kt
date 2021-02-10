package com.app.dubaiculture.data.repository.explore.local.models

data class UpComingEvents(
    val id: Int,
    val title: String?="",
    val category: String?="",
    val location: String?="",
    val start_date: String?="",
    val end_date: String?="",
    val price_tag: String?="",
    val image_url:String?="",
    val is_liked: Boolean,
    val date: String?="",
    val locale:String ?= ""

)
