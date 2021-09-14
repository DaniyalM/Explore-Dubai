package com.app.dubaiculture.data.repository.more.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.dubaiculture.data.repository.more.remote.response.notification.NotificationDTO
import com.app.dubaiculture.data.repository.more.remote.response.notification.NotificationRequestDTO
import com.app.dubaiculture.data.repository.more.service.MoreService
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequestDTO
import com.app.dubaiculture.data.repository.news.remote.service.NewsService
import com.app.dubaiculture.utils.Constants

class NotificationPagingSource (
    private val moreService: MoreService,
    private val notificationRequestDTO: NotificationRequestDTO,

    ) :
    PagingSource<Int, NotificationDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationDTO> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = moreService.getMyNotification(
                pageNo= nextPageNumber,
                pageSize = Constants.PAGING.NOTIFICATION_PAGE_SIZE * nextPageNumber,
//                culture = newsRequestDTO.culture
            )
            LoadResult.Page(
                data = response.Result.Notifications,
                prevKey = null,
                nextKey = if (response.Result.Notifications.size < Constants.PAGING.NEW_PAGING_SIZE) null else nextPageNumber.plus(
                    1
                )
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, NotificationDTO>): Int? {
        return state.anchorPosition
    }
}
