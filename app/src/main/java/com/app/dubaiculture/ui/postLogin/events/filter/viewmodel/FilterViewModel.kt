package com.app.dubaiculture.ui.postLogin.events.filter.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.repository.filter.Categories
import com.app.dubaiculture.data.repository.filter.CategoriesRepository
import com.app.dubaiculture.data.repository.filter.models.FilterData
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FilterViewModel @ViewModelInject constructor(
    private val categoriesRepository: CategoriesRepository,
    application: Application,
) : BaseViewModel(application) {


    private val _categories: MutableLiveData<List<Categories>> = MutableLiveData()
    val categories: LiveData<List<Categories>> = _categories

     val _filterDataDta: MutableLiveData<ArrayList<FilterData>> = MutableLiveData()
    val filterDataData: LiveData<ArrayList<FilterData>> = _filterDataDta

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
}
