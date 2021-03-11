package com.app.dubaiculture.data.repository.attraction

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.mapper.transformAttractionCategory
import com.app.dubaiculture.data.repository.attraction.mapper.transformAttractionCategoryRequest
import com.app.dubaiculture.data.repository.attraction.mapper.transformAttractionDetail
import com.app.dubaiculture.data.repository.attraction.mapper.transformAttractionDetailRequest
import com.app.dubaiculture.data.repository.attraction.remote.AttractionRDS
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.data.repository.base.BaseRepository
import javax.inject.Inject

class AttractionRepository @Inject constructor(
    private val attractionRDS: AttractionRDS,
) : BaseRepository() {
    suspend fun getAttractionCategories(attractionRequest: AttractionRequest): Result<ArrayList<AttractionCategory>> {
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


//    suspend fun getAttractionsByCategory(attractionRequest: AttractionRequest): Result<List<Attractions>> {
//        return when (val resultRDS =
//            attractionRDS.getAttractionDetail(transformAttractionDetailRequest(
//                attractionRequest))) {
//            is Result.Success -> {
//                val attractionRds = resultRDS
//                if (attractionRds.value.statusCode != 200) {
//                    Result.Failure(true, attractionRds.value.statusCode, null)
//                } else {
//                    val attractionLds = transformAttractionDetail(attractionRds.value)
//                    Result.Success(attractionLds)
//                }
//            }
//            is Result.Error -> resultRDS
//            is Result.Failure -> resultRDS
//        }
//    }

    suspend fun getAttractionDetail(attractionRequest: AttractionRequest): Result<Attractions> {
        return when (val resultRDS =
            attractionRDS.getAttractionDetail(transformAttractionDetailRequest(
                attractionRequest))) {
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


}