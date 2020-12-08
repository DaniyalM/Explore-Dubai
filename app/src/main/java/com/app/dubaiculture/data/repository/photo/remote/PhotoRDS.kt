package com.app.dubaiculture.data.repository.photo.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.photo.remote.request.GetPhotoRequest
import com.app.dubaiculture.data.repository.photo.remote.response.PhotoDTO
import com.app.dubaiculture.data.repository.photo.remote.service.PhotoService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.app.dubaiculture.data.Result

class PhotoRDS @Inject constructor(private val photoService: PhotoService) : BaseRDS() {

    suspend fun getPhotos() = safeApiCall {
        photoService.getPhotos(GetPhotoRequest())
    }

    suspend fun getPaginatedPhotos(): Result<Flow<PagingData<PhotoDTO>>> {
        return safeApiCall {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { PhotoPagingSource(photoService) }
            ).flow
        }

    }
//
//    suspend fun getPhotos(): Result<PhotosResponse> {
//        return withContext(Dispatchers.IO) {
//
//            try {
//                val response = photoService.getPhotos(GetPhotoRequest())
//                // specify dispatcher here so no viewmodel has to specify explicitly
//                if (response.isSuccessful) {
//                    Result.Success(response.body()!!, "Request Successful")
//                } else {
//                    Result.Error(Exception("Request Failed - unsuccessful"))
//
//                }
//            } catch (e: Exception) {
//                Timber.e(e.message)
//                Result.Error(Exception("Request Failed - parsing error"))
//
//            }
//
//        }
//
//    }
}