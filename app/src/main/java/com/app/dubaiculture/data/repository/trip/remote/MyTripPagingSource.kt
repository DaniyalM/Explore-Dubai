package com.app.dubaiculture.data.repository.trip.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequestDTO
import com.app.dubaiculture.data.repository.trip.remote.response.MyTripResponseDTO
import com.app.dubaiculture.data.repository.trip.remote.response.Trip
import com.app.dubaiculture.data.repository.trip.service.TripService
import com.app.dubaiculture.utils.Constants

class MyTripPagingSource(
    private val tripService: TripService,
    private val culture: String,
) :
    PagingSource<Int, Trip>() {
    override fun getRefreshKey(state: PagingState<Int, Trip>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Trip> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = tripService.getMyTrips(
                pageNumber = nextPageNumber,
                pageSize = Constants.PAGING.ATTRACTION_PAGING_SIZE * nextPageNumber,
                culture = culture
            )
            LoadResult.Page(
                data = response.myTripResponseDTO.Trips,
                prevKey = null,
                nextKey = if (response.myTripResponseDTO.Trips.size < Constants.PAGING.ATTRACTION_PAGING_SIZE) null else nextPageNumber.plus(
                    1
                )
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)

        }
    }
}