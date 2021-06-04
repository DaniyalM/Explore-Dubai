package com.app.dubaiculture.data.repository.profile.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MultipartFormHelpers {
    fun getMultiPartData(imageURIPath: String): MultipartBody.Part? {
        return try {
            val f = File(imageURIPath)
            // create RequestBody instance from file
//            val requestFile: RequestBody = f.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            val requestFile = f.asRequestBody("image/*".toMediaTypeOrNull())
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part.createFormData("File", f.name, requestFile)
        } catch (e: Exception) {
            null
        }
    }

    fun getMultiPartVideoData(imageURIPath: String): MultipartBody.Part {
        val f = File(imageURIPath)
        // create RequestBody instance from file
        val requestFile = f.asRequestBody("video/mp4".toMediaTypeOrNull())
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData("file", f.name, requestFile)
    }

    fun getRequestBody(data: String?): RequestBody {
        // add another part within the multipart request
        return RequestBody.create(
                MultipartBody.FORM, data!!
        )
    }
}