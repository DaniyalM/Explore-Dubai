package com.dubaiculture.data.repository.photo.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dubaiculture.data.repository.photo.remote.response.PhotoDTO
import com.dubaiculture.data.repository.photo.remote.service.PhotoService

class PhotoPagingSource(private val photoService: PhotoService) :
    PagingSource<Int, PhotoDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDTO> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = photoService.getPhotos(nextPageNumber)
            LoadResult.Page(
                data = response.photos,
                prevKey = null,
                nextKey = if (nextPageNumber == 3) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoDTO>): Int? {
        return state.anchorPosition
    }
}
