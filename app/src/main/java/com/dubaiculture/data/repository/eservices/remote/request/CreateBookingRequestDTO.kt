package com.dubaiculture.data.repository.eservices.remote.request

data class CreateBookingRequestDTO(
    val FullName: String,
    val Location: String,
    val Email: String,
    val BookingType: String,
    val NumberOfVisitors: String,
    val PhoneNumber: String,
    val Nationality: String,
    val Company: String,
    val Duration: String,
    val Additionalservicesrequest: String,
    val Date: String,
    val DateTime: String,
    val Disclaimer: String,
    val Entity: String,
    val CreatedDate: String,
    val SerialNumber: String,
    val ApproverUser: String,
    val ApprovedDatetime: String,
    val ApprovalStatus: String,
    val ApprovalComments: String,
    val BookingForm: String,
    val StatusChange0: String,
    val StatusChange1: String,
    val Status: String
)