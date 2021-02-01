package com.app.dubaiculture.data.repository.explore.local.models

data class InnerValue(
    val id:Int,
    val title:String,
    val image_url:String,
    val locale:String,
    val category: String,
    val is_liked:Boolean,
    val location:String,
    val starting_date:String,
    val ending_data:String,
    val price_tag:String,
    val date:String
    )
