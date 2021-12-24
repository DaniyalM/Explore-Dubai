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
                fullName = createNocRequestDTO.FullName,
                userType = createNocRequestDTO.UserType,
                subject = createNocRequestDTO.Subject,
                filmingDate = createNocRequestDTO.FilmingDate,
                fromTime = createNocRequestDTO.FromTime,
                toTime = createNocRequestDTO.ToTime,
                contactPhoneNumber = createNocRequestDTO.ContactPhoneNumber,
                companyDepartment = createNocRequestDTO.CompanyDepartment,
                signature = createNocRequestDTO.Signature,
                userEmailId = createNocRequestDTO.UserEmailID,
                status = createNocRequestDTO.Status,
                statusComments = createNocRequestDTO.StatusComments,
                locationAddress = createNocRequestDTO.LocationAddress
            )
        }

}