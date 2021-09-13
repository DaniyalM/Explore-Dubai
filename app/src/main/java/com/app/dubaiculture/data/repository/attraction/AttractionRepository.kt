package com.app.dubaiculture.data.repository.attraction

import androidx.paging.PagingData
import androidx.paging.map
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.mapper.*
import com.app.dubaiculture.data.repository.attraction.remote.AttractionRDS
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.mapper.transformNewsPaging
import com.app.dubaiculture.data.repository.news.mapper.transformNewsRequest
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AttractionRepository @Inject constructor(
        private val attractionRDS: AttractionRDS,
) : BaseRepository(attractionRDS) {
    suspend fun getAttractionCategories(attractionRequest: AttractionRequest): Result<List<AttractionCategory>> {
        return when (val resultRDS = attractionRDS.getAttractionCategories(
                transformAttractionCategoryRequest(attractionRequest))) {
            is Result.Success -> {
                // Single Source Of Truth -> get data from server -> save to db -> get from db to provide to UI
                val listRDS = resultRDS
                if (listRDS.value.statusCode != 200) {
                    Result.Failure(true, listRDS.value.statusCode, null)
                } else {
                    val listLDS = transformAttractionCategory(listRDS.value)
                    Result.Success(listLDS)
//                photoLDS.insertAll(listLDS as MutableList<Photo>)
//                val resultLDS = photoLDS.getAll()
                }
            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS
        }
    }

    suspend fun getAttractionsByCategory(attractionRequest: AttractionRequest): Result<Flow<PagingData<Attractions>>> {
        val result = attractionRDS.getAttractionsListingByCategory((transformAttractionsRequest(attractionRequest)))
        return if (result is Result.Success) {
            Result.Success(result.value.map {
                it.map {
                    transformAttractionDetail(it)
                }
            })

        } else {
            Result.Failure(true, null, null, Constants.Error.SOMETHING_WENT_WRONG)
        }
    }

//
//    suspend fun getAttractionsByCategory(attractionRequest: AttractionRequest): Result<List<Attractions>> {
//        return when (val resultRDS =
//                attractionRDS.getAttractionsListingByCategory(transformAttractionsRequest(
//                        attractionRequest))) {
//            is Result.Success -> {
//                val attractionRds = resultRDS
//                if (attractionRds.value.statusCode != 200) {
//                    Result.Failure(true, attractionRds.value.statusCode, null)
//                } else {
//                    val attractionLds = transformAttractions(attractionRds.value)
//                    Result.Success(attractionLds)
//                }
//            }
//            is Result.Error -> resultRDS
//            is Result.Failure -> resultRDS
//        }
//    }

    suspend fun getAttractionDetail(attractionRequest: AttractionRequest): Result<Attractions> {
        return when (val resultRDS =
                attractionRDS.getAttractionDetail(transformAttractionDetailRequest(attractionRequest))) {
            is Result.Success -> {
                val attractionRds = resultRDS
                if (attractionRds.value.statusCode != 200) {
                    Result.Failure(true, attractionRds.value.statusCode, null)
                } else {
                    val attractionLds = transformAttractionDetail(attractionRds.value)
                    Result.Success(attractionLds)
                }
            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS
        }
    }

    suspend fun getAttractionDetailForThreeSixty(attractionRequest: AttractionRequest): Result<Attractions> {
        return when (val resultRDS =
                attractionRDS.getAttractionDetailForThreeSixty(transformAttractionDetailRequest(attractionRequest))) {
            is Result.Success -> {
                val attractionRds = resultRDS
                if (attractionRds.value.statusCode != 200) {
                    Result.Failure(true, attractionRds.value.statusCode, null)
                } else {
                    val attractionLds = transformAttractionDetail(attractionRds.value)
                    Result.Success(attractionLds)
                }
            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS
        }
    }

    suspend fun getVisitedPlaces(
//            attractionRequest: AttractionRequest
    ) =
            when (val resultRDS = attractionRDS.getVisitedPlaces(
//                    transformVisitedAttractionsRequest(attractionRequest)
            )) {
                is Result.Success -> {
                    if (resultRDS.value.succeeded) {
                        Result.Success(Event(transformAttractions(resultRDS.value)))
                    } else {
                        Result.Failure(false, null, null, resultRDS.value.errorMessage)
                    }

                }
                is Result.Error -> resultRDS
                is Result.Failure -> resultRDS
            }


}