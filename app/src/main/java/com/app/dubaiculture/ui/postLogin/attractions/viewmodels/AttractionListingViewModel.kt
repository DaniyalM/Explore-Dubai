package com.app.dubaiculture.ui.postLogin.attractions.viewmodels

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.remote.request.AttractionRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_CAT_OBJECT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AttractionListingViewModel @Inject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application, attractionRepository) {
    private val _attractionPagination: MutableLiveData<PagingData<Attractions>> = MutableLiveData()
    val attractionPagination: LiveData<PagingData<Attractions>> = _attractionPagination
    private val context = getApplication<ApplicationEntry>()


    init {
        savedStateHandle.get<AttractionCategory>(ATTRACTION_CAT_OBJECT)?.let {
            getAttractionThroughCategory(
                categoryId = it.id,
                locale = context.auth.locale.toString()
            )
        }
    }


    fun getAttractionThroughCategory(
        categoryId: String?,
        locale: String,
    ) {
        viewModelScope.launch {
            when (val result = attractionRepository.getAttractionsByCategory(
                AttractionRequest(
                    attractionCategoryId = categoryId,
                    culture = locale
                )
            )) {
                is Result.Success -> {
                    result.value
                        .cachedIn(viewModelScope)
                        .collectLatest {
                            _attractionPagination.value = it
                        }
                }
                is Result.Failure -> {
                    showLoader(false)

                }

            }
        }
    }

}