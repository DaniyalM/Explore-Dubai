package com.dubaiculture.ui.postLogin.popular_service.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.popular_service.ServiceRepository
import com.dubaiculture.data.repository.popular_service.local.models.EService
import com.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularServiceViewModel @Inject constructor(
    application: Application,
    private val serviceRepository: ServiceRepository,
) : BaseViewModel(application, serviceRepository) {

    var serviceId: String = ""
    var id: String = ""

//    private val _eServices: MutableLiveData<Result<EServices>> = MutableLiveData()
//    val eServices: LiveData<Result<EServices>> = _eServices

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
//                    _eServices.value = result
                    _serviceListCategory.value = Event(result.value.serviceCategory)
                    _serviceList.value = result.value.eServices
                    _serviceListTemp.value = _serviceList.value
                    updateServiceList(serviceId)

                }
                is Result.Failure -> {
                    showLoader(false)
//                    _eServices.value = result
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

//    fun returnSearchList(
//        searchList: ArrayList<EService>,
//        editTextKey: EditText,
//        adapter: GroupAdapter<GroupieViewHolder>,
//        imgCancelBtn: ImageView,
//        context: Context
//    ) {
//        adapter.clear()
//        val myFilterSearchList = searchList.filter {
//            it.title.contains(editTextKey.text.toString().trim(), ignoreCase = true)
//        }
//        if (editTextKey.text.toString().trim().isNullOrEmpty()) {
//            imgCancelBtn.visibility = View.GONE
//            searchList.map {
//                adapter.add(
//                    PopularServiceListItem<ItemsServiceListingLayoutBinding>(
//                        eService = it,
//                        resLayout = R.layout.items_service_listing_layout,
//                        context = context
//
//                    )
//                )
//            }
//        } else {
//            imgCancelBtn.visibility = View.VISIBLE
//            myFilterSearchList.map {
//                adapter.add(
//                    PopularServiceListItem<ItemsServiceListingLayoutBinding>(
//                        eService = it,
//                        resLayout = R.layout.items_service_listing_layout,
//                        context = context
//                    )
//                )
//            }
//        }
//        adapter.notifyDataSetChanged()
//
//    }
}