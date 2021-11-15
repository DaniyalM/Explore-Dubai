package com.dubaiculture.data.repository.eservices.service

import com.dubaiculture.data.repository.base.BaseService
import com.dubaiculture.data.repository.eservices.remote.request.GetFieldValueRequestDTO
import com.dubaiculture.data.repository.eservices.remote.request.GetTokenRequestParam
import com.dubaiculture.data.repository.eservices.remote.response.GetFieldValueResponse
import com.dubaiculture.data.repository.eservices.remote.response.GetTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface EService : BaseService {

    @GET("Home/GetToken")
    suspend fun getEServiceToken(@Body getTokenRequestParam: GetTokenRequestParam): GetTokenResponse

//    @POST("FieldValue/GetFieldValue")
//    suspend fun getFieldValue(@Body getFieldValueRequestDTO: GetFieldValueRequestDTO): GetFieldValueResponse

    @GET("FieldValue/GetFieldValue")
    suspend fun getFieldValue(): GetFieldValueResponse

}