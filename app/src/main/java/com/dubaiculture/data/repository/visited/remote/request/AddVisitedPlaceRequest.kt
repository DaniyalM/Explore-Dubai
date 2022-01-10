package com.dubaiculture.data.repository.visited.remote.request

class AddVisitedPlaceRequest(
    val itemId: String,
    val deviceId: String,
    val type: Int,
    val addedOn: String
)

//{
//    "ItemID": "string",
//    "DeviceID": "string",
//    "Type": 0,
//    "AddedOn": "string"
//}