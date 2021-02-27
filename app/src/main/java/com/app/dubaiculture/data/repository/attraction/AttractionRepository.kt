package com.app.dubaiculture.data.repository.attraction

import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.mapper.transformAttractionCategory
import com.app.dubaiculture.data.repository.attraction.mapper.transformAttractionCategoryRequest
import com.app.dubaiculture.data.repository.attraction.remote.AttractionRDS
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionCategoryRequest
import com.app.dubaiculture.data.repository.base.BaseRepository
import javax.inject.Inject

class AttractionRepository @Inject constructor(
    private val attractionRDS: AttractionRDS,
) : BaseRepository() {
    suspend fun getAttractionCategories(attractionCategoryRequest: AttractionCategoryRequest): Result<List<AttractionCategory>>? {
        return when (val resultRDS = attractionRDS.getAttractionCategories(
            transformAttractionCategoryRequest(attractionCategoryRequest))) {
            is Result.Success -> {
                // Single Source Of Truth -> get data from server -> save to db -> get from db to provide to UI
                val listRDS = resultRDS
                if (listRDS.value.statusCode != 200) {
                    Result.Failure(true, listRDS.value.statusCode, null)
                } else {
                    listRDS.value.Result.attractionsCategories.let {
                        val listLDS = transformAttractionCategory(it)
                        Result.Success(listLDS)
                    }

//                photoLDS.insertAll(listLDS as MutableList<Photo>)
//                val resultLDS = photoLDS.getAll()

                }

            }
            is Result.Error -> resultRDS
            is Result.Failure -> resultRDS
        }

    }
}