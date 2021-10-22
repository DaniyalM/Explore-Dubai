package com.app.dubaiculture.ui.postLogin.popular_service.viewmodels

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.ServiceRepository
import com.app.dubaiculture.data.repository.popular_service.local.models.EService
import com.app.dubaiculture.data.repository.popular_service.local.models.EServices
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.app.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.app.dubaiculture.databinding.ItemsServiceListingLayoutBinding
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListItem
import com.app.dubaiculture.utils.event.Event
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularServiceViewModel @Inject constructor(
    application: Application,
    private val serviceRepository: ServiceRepository,
) : BaseViewModel(application, serviceRepository) {

    var id: String = ""

    private val _eServices: MutableLiveData<Result<EServices>> = MutableLiveData()
    val eServices: LiveData<Result<EServices>> = _eServices

    private val _serviceList: MutableLiveData<List<EService>> = MutableLiveData()
    private val _serviceListTemp: MutableLiveData<List<EService>> = MutableLiveData()
    val serviceList: LiveData<List<EService>> = _serviceListTemp

    private val _serviceListCategory: MutableLiveData<Event<List<ServiceCategory>>> =
        MutableLiveData()
    val serviceListCategory: LiveData<Event<List<ServiceCategory>>> = _serviceListCategory


    private val context = getApplication<ApplicationEntry>()

    init {
        getEServicesToScreen(context.auth.locale.toString())
    }


    fun getEServicesToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = serviceRepository.getEServices(EServiceRequest(culture = locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _eServices.value = result
                    _serviceListCategory.value = Event(result.value.serviceCategory)
                    _serviceList.value = result.value.eServices
                    _serviceListTemp.value = _serviceList.value
                    updateServiceList(result.value.serviceCategory[0].id)

                }
                is Result.Failure -> {
                    showLoader(false)
                    _eServices.value = result
                }

            }
        }
    }

    fun updateServiceList(id: String) {
        val data = _serviceList.value ?: return
        data.filter { it.categoryId == id }
            .let {
                _serviceListTemp.value = it
            }
    }

    fun searchFaq(keyword: String) {
        val data = _serviceList.value ?: return

        if (keyword.isNotEmpty()) {
            data.filter {
                keyword in it.title && it.categoryId == id
            }.let {
                if (it.isNotEmpty())
                    _serviceListTemp.value = it
            }
        } else {
            updateServiceList(id)
        }
    }

    fun onSearchTextChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        searchFaq(s.toString())
    }

    fun returnFilterList(
        categoryID: String? = null,
        list: List<EService>? = null
    ): MutableList<EService> {
        /*temporary for testing*/
        val getFilterData = list?.filter {
            true
        }
        /*when API will fix id match eService item ID*/
//        val getFilterData = eServicesList.filter {
//            it.id == categoryID
//        }
        return getFilterData as MutableList<EService>
    }

    fun returnSearchList(
        searchList: ArrayList<EService>,
        editTextKey: EditText,
        adapter: GroupAdapter<GroupieViewHolder>,
        imgCancelBtn: ImageView,
        context: Context
    ) {
        adapter.clear()
        val myFilterSearchList = searchList.filter {
            it.title.contains(editTextKey.text.toString().trim(), ignoreCase = true)
        }
        if (editTextKey.text.toString().trim().isNullOrEmpty()) {
            imgCancelBtn.visibility = View.GONE
            searchList.map {
                adapter.add(
                    PopularServiceListItem<ItemsServiceListingLayoutBinding>(
                        eService = it,
                        resLayout = R.layout.items_service_listing_layout,
                        context = context

                    )
                )
            }
        } else {
            imgCancelBtn.visibility = View.VISIBLE
            myFilterSearchList.map {
                adapter.add(
                    PopularServiceListItem<ItemsServiceListingLayoutBinding>(
                        eService = it,
                        resLayout = R.layout.items_service_listing_layout,
                        context = context
                    )
                )
            }
        }
        adapter.notifyDataSetChanged()

    }
}