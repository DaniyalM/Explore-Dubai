package com.dubaiculture.data.repository.eservices.remote

import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.base.BaseRDS
import com.dubaiculture.data.repository.eservices.remote.request.CreateNocRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.data.repository.eservices.remote.response.FormResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetTokenResponse
import com.dubaiculture.data.repository.eservices.service.EService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class EServicesRDS @Inject constructor(
    private val eService: EService
) : BaseRDS() {
    suspend fun getEServiceToken(getTokenRequestParam: GetTokenRequestParam): Result<GetTokenResponse> =
        safeApiCall {
            eService.getEServiceToken(
                username = getTokenRequestParam.UserName.toRequestBody("text/plain".toMediaType()),
                password = getTokenRequestParam.Password.toRequestBody("text/plain".toMediaType())
            )
        }

    suspend fun getFieldValue(
        token: String,
        getFieldValueRequestDTO: GetFieldValueRequestDTO
    ): Result<GetFieldValueResponse> =
        safeApiCall {
            eService.getFieldValue(
                token,
                getFieldValueRequestDTO.FormName.toRequestBody("text/plain".toMediaType())
            )
        }

    suspend fun createNoc(createNocRequestDTO: CreateNocRequestDTO): Result<FormResponse> =
        safeApiCall {
            eService.createNoc(
                title = createNocRequestDTO.Title.toRequestBody("text/plain".toMediaType()),
                userType = createNocRequestDTO.UserType.toRequestBody("text/plain".toMediaType()),
                subject = createNocRequestDTO.Subject.toRequestBody("text/plain".toMediaType()),
                filmingDate = createNocRequestDTO.FilmingDate.toRequestBody("text/plain".toMediaType()),
                fromTime = createNocRequestDTO.FromTime.toRequestBody("text/plain".toMediaType()),
                toTime = createNocRequestDTO.ToTime.toRequestBody("text/plain".toMediaType()),
                contactPhoneNumber = createNocRequestDTO.ContactPhoneNumber.toRequestBody("text/plain".toMediaType()),
                fullName = createNocRequestDTO.FullName.toRequestBody("text/plain".toMediaType()),
                locationAddress = createNocRequestDTO.LocationAddress.toRequestBody("text/plain".toMediaType()),
                userEmailId = createNocRequestDTO.UserEmailID.toRequestBody("text/plain".toMediaType()),
                file = createNocRequestDTO.file
            )
        }


}

//@Part("Title") title: RequestBody,
//@Part("UserType") userType: RequestBody,
//@Part("Subject") subject: RequestBody,
//@Part("FilmingDate") filmingDate: RequestBody,
//@Part("FromTime") fromTime: RequestBody,
//@Part("ToTime") toTime: RequestBody,
//@Part("ContactPhoneNumber") contactPhoneNumber: RequestBody,
//@Part("FullName") fullName: RequestBody,
//@Part("LocationAddress") locationAddress: RequestBody,
//@Part("UserEmailID") userEmailId: RequestBody,
//@Part file: MultipartBody.Part? = null