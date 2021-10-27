package com.app.dubaiculture.data.repository.more.remote.response.notification

data class NotificationDTO(
    val Body: String?,
    val DateTime: String?,
    val Id: Int?,
    val UserId: String?,
    val ItemId: String?,
    val Type: Int?,
//    val Icon: Any,
    val Title: String?
)
//) {"Id":27,"UserId":null,"ItemId":"E0FB335B7B464F1F912A1C7C62B507FE","Title":"","Body":"Event Certified Cultural Guide has been cancelled","Type":0,"CreatedOn":"2021-10-26T15:20:18.563","UpdatedOn":"2021-10-26T15:20:18.563","Status":true,"Culture":"en","Topic":"'IOSBroadcast_en' in topics || 'AndroidBroadcast_en' in topics" }