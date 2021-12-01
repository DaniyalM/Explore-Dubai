package com.dubaiculture.data.repository.eservices.remote.request

data class CreateBookingRequest(
    val location: String,
    val date: String,
    val dateTime: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val duration: String,
    val additionalServicesRequest: String,
    val bookingType: String,
    val company: String,
    val entity: String,
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