package com.app.dubaiculture.data.repository.filter.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.filter.Categories
import javax.inject.Inject

class CategoriesRDS @Inject constructor() : BaseRDS()  {

    suspend fun getCategories() :List<Categories>{
        val list = mutableListOf<Categories>()
        list.add(Categories("Visual Studio",false))
        list.add(Categories("Performance",true))
        list.add(Categories("Talks",false))
        list.add(Categories("Workshops",false))
        list.add(Categories("Members",false))
        list.add(Categories("Family",true))
        list.add(Categories("Special Events",false))
        return list
    }





}