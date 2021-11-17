package com.dubaiculture.data.repository.eservices.remote.request

import okhttp3.MultipartBody

data class CreateNocRequest(
    val title: String,
    val userType: String,
    val subject: String,
    val filmingDate: String,
    val fromTime: String,
    val toTime: String,
    val contactPhoneNumber: String,
    val fullName: String,
    val locationAddress: String,
    val userEmailID: String,
    val countryCode: String,
    val file: MultipartBody.Part? = null
)


//Title	string	Title
//UserType	String	User Type
//Subject	string	Subject For NOC Form
//FilmingDate	string	Filming Date in MM/dd/yyyy format( ex. 10/30/2021)
//FromTime	string	From Time in hh:mm tt Format (ex. 11:20 AM)
//ToTime	string	To Time in hh:mm tt Format (ex. 11:20 AM)
//ContactPhoneNumber	string	contact no (ex. 7894561230)
//FullName	string	User Full Name
//LocationAddress	String	Location Address
//UserEmailID	String	User Email Address
//countryCode	String	countryCode Number (ex. 971)
//file	File	Attach Signature Image Here
