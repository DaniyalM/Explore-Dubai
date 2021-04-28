package com.app.dubaiculture.data.repository.event.remote.request

class AddToFavouriteRequest(
    val  userId : String?="",
    val  itemId : String?="",
    val  type : Int?=null
)