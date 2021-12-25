package com.dubaiculture.ui.postLogin.events.detail.helper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MultipartFormHelper {
    fun getMultiPartData(imageURIPath: String): MultipartBody.Part? {
        if (imageURIPath.isEmpty()) return null
        return try {
            val f = File(imageURIPath)
            // create RequestBody instance from file
            val requestFile = f.asRequestBody("image/*".toMediaTypeOrNull())
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part.createFormData("file", f.name, requestFile)
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

    //${f.extension}
    fun getRequestBody(data: String?): RequestBody {
        // add another part within the multipart request
        return RequestBody.create(
            MultipartBody.FORM, data!!
        )
    }
}