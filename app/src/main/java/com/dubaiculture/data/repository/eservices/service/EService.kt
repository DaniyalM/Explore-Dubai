package com.dubaiculture.data.repository.eservices.service

import com.dubaiculture.data.repository.base.BaseService
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
    @POST("Validation/GetFieldAndValidation")//FieldValue/GetFieldValue
    suspend fun getFieldValue(
        @Header("token") token: String,
        @Part("FormName") formName: RequestBody
    ): GetFieldValueResponse

    @Multipart
    @POST("{url}")
    suspend fun submitForm(
        @Header("Token") token: String,
        @Header("Language") language: String,
        @PartMap params: HashMap<String, RequestBody>,
        @Path(value = "url", encoded = true) url: String
    ): FormResponse

}