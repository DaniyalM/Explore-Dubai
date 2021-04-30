package com.app.dubaiculture.data.repository.filter

import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.filter.remote.CategoriesRDS
import javax.inject.Inject

class CategoriesRepository  @Inject constructor(private val categoriesRDS: CategoriesRDS):
    BaseRepository() {
    suspend fun getCategories():List<Categories> = categoriesRDS.getCategories()

}