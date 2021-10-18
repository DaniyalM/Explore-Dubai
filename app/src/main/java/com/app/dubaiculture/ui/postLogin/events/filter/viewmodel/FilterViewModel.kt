package com.app.dubaiculture.ui.postLogin.events.filter.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.filter.Categories
import com.app.dubaiculture.data.repository.filter.CategoriesRepository
import com.app.dubaiculture.data.repository.filter.models.SelectedItems
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    application: Application,
) : BaseViewModel(application) {


    private val _categories: MutableLiveData<List<Categories>> = MutableLiveData()
    val categories: LiveData<List<Categories>> = _categories

    val _filterDataDta: MutableLiveData<ArrayList<EventRequest>> = MutableLiveData()
    val filterDataData: LiveData<ArrayList<EventRequest>> = _filterDataDta

    init {
        showCategories()
    }

    private fun showCategories() {
        viewModelScope.launch {
            categoriesRepository.getCategories().let {
                _categories.value = it
            }
        }
    }


    fun getSelectedCategory(categoryList: MutableList<Filter>): ArrayList<String> {
        val categoryStringList = ArrayList<String>()
        categoryList.forEach {
            if (it.isSelected) {
                categoryStringList.add(it.id.toString())
            }
        }
        return categoryStringList
    }

    fun getSelectedCategory1(categoryList: MutableList<Filter>): ArrayList<SelectedItems> {
        val categoryStringList = ArrayList<SelectedItems>()
        categoryList.forEach {
            if (it.isSelected) {
                categoryStringList.add(SelectedItems(it.title))
            }
        }
        return categoryStringList
    }
}
