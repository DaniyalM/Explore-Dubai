package com.dubaiculture.data.repository.eservices.remote.request


data class CreateBookingRequestDTO (
    val Location: String,
    val Date: String,
    val DateTime: String,
    val FullName: String,
    val Email: String,
    val PhoneNumber: String,
    val Duration: String,
    val Additionalservicesrequest: String,
    val BookingType: String,
    val Company: String,
    val Entity: String,
    val countryCode: String
)

//@Part("Location") Location: RequestBody,
//@Part("Date") Date: RequestBody,
//@Part("DateTime") DateTime: RequestBody,
//@Part("FullName") FullName: RequestBody,
//@Part("Email") Email: RequestBody,
//@Part("PhoneNumber") PhoneNumber: RequestBody,
//@Part("Duration") Duration: RequestBody,
//@Part("Additionalservicesrequest") Additionalservicesrequest: RequestBody,
//@Part("BookingType") BookingType: RequestBody,
//@Part("Company") Company: RequestBody,
//@Part("Entity") Entity: RequestBody,
//@Part("countryCode") countryCode: RequestBody