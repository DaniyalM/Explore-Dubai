package com.dubaiculture.data.repository.eservices.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.data.repository.eservices.remote.response.FormResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetTokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EService : BaseService {

    @Multipart
    @POST("Home/GetToken")
    suspend fun getEServiceToken(
        @Part("UserName") username: RequestBody,
        @Part("Password") password: RequestBody
    ): GetTokenResponse

    @Multipart
    @POST("FieldValue/GetFieldValue")
    suspend fun getFieldValue(
        @Header("token") token: String,
        @Part("FormName") formName: RequestBody
    ): GetFieldValueResponse

    @Multipart
    @POST("NOC/CreateNOC")
    suspend fun createNoc(
        @Part("Title") title: RequestBody,
        @Part("UserType") userType: RequestBody,
        @Part("Subject") subject: RequestBody,
        @Part("FilmingDate") filmingDate: RequestBody,
        @Part("FromTime") fromTime: RequestBody,
        @Part("ToTime") toTime: RequestBody,
        @Part("ContactPhoneNumber") contactPhoneNumber: RequestBody,
        @Part("FullName") fullName: RequestBody,
        @Part("LocationAddress") locationAddress: RequestBody,
        @Part("UserEmailID") userEmailId: RequestBody,
        @Part file: MultipartBody.Part? = null
    ): FormResponse

    @Multipart
    @POST("Supplier/SupplierRegistration")
    suspend fun registerSupplier(
        @Part("RegistrationType") RegistrationType: RequestBody,
        @Part("EntryDate") EntryDate: RequestBody,
        @Part("LicenseNumber") LicenseNumber: RequestBody,
        @Part("CompanyName") CompanyName: RequestBody,
        @Part("LicenseActivities") LicenseActivities: RequestBody,
        @Part("TradeLicenseIssueDate") TradeLicenseIssueDate: RequestBody,
        @Part("TradeLicenseExpiryDate") TradeLicenseExpiryDate: RequestBody,
        @Part("TradeLicenseIssuePlace") TradeLicenseIssuePlace: RequestBody,
        @Part("ManagerName") ManagerName: RequestBody,
        @Part("OwnerName") OwnerName: RequestBody,
        @Part("TradeName") TradeName: RequestBody,
        @Part("Country") Country: RequestBody,
        @Part("City") City: RequestBody,
        @Part("Mobile") Mobile: RequestBody,
        @Part("OfficeTelephoneNumber") OfficeTelephoneNumber: RequestBody,
        @Part("Email") Email: RequestBody,
        @Part("POBox") POBox: RequestBody,
        @Part("RegisteredInTejari") RegisteredInTejari: RequestBody,
        @Part("DubaiSME") DubaiSME: RequestBody,
        @Part commerciallicense: MultipartBody.Part,
        @Part companyprofile: MultipartBody.Part,
        @Part passport: MultipartBody.Part,
        @Part listofmaterials: MultipartBody.Part,
        @Part commercelicense: MultipartBody.Part,
        @Part previousexperience: MultipartBody.Part,
        @Part contractpartnership: MultipartBody.Part,
        @Part membershipcertificate: MultipartBody.Part,
        @Part certificatepowerattorney: MultipartBody.Part,
    ): FormResponse

    @Multipart
    @POST("Booking/CreateBooking")
    suspend fun createBooking(
        @Part("Location") Location: RequestBody,
        @Part("Date") Date: RequestBody,
        @Part("DateTime") DateTime: RequestBody,
        @Part("FullName") FullName: RequestBody,
        @Part("Email") Email: RequestBody,
        @Part("PhoneNumber") PhoneNumber: RequestBody,
        @Part("Duration") Duration: RequestBody,
        @Part("Additionalservicesrequest") Additionalservicesrequest: RequestBody,
        @Part("BookingType") BookingType: RequestBody,
        @Part("Company") Company: RequestBody,
        @Part("Entity") Entity: RequestBody,
        @Part("countryCode") countryCode: RequestBody
    ): FormResponse


    @POST("CreateRentRequest")
    suspend fun createRentRequest(
        @Part("FullName") RegistrationType: RequestBody,
        @Part("PriorDate") EntryDate: RequestBody,
        @Part("Nationality") LicenseNumber: RequestBody,
        @Part("EmiratesId") CompanyName: RequestBody,
        @Part("PassportNo") LicenseActivities: RequestBody,
        @Part("Address") TradeLicenseIssueDate: RequestBody,
        @Part("ContactPhoneNumber") TradeLicenseExpiryDate: RequestBody,
        @Part("UserEmailID") TradeLicenseIssuePlace: RequestBody,
        @Part("RealEstateType") ManagerName: RequestBody,
        @Part("TotalRequiredSpace") OwnerName: RequestBody,
        @Part("Measurement") TradeName: RequestBody,
        @Part("countryCode") Country: RequestBody
    ): FormResponse

}