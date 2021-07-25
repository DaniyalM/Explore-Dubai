package com.app.dubaiculture.ui.postLogin.popular_service.viewmodels

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.popular_service.ServiceRepository
import com.app.dubaiculture.data.repository.popular_service.local.models.EService
import com.app.dubaiculture.data.repository.popular_service.local.models.EServices
import com.app.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.app.dubaiculture.databinding.ItemsServiceListingLayoutBinding
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.launch

class PopularServiceViewModel @ViewModelInject constructor(
    application: Application,
    private val serviceRepository: ServiceRepository,
) : BaseViewModel(application, serviceRepository) {
    private val _eServices: MutableLiveData<Result<EServices>> = MutableLiveData()
    val eServices: LiveData<Result<EServices>> = _eServices
    private val context = getApplication<ApplicationEntry>()

    init {
        getEServicesToScreen(context.auth.locale.toString())
    }

    fun getEServicesToScreen(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = serviceRepository.getEServices(EServiceRequest(culture = locale))) {
                is Result.Success -> {
                    _eServices.value = result
                    showLoader(false)
                }
                is Result.Failure -> {
                    showLoader(false)
                    _eServices.value = result
                }

            }
        }
    }

    fun returnFilterList(categoryID:String?=null,list: List<EService>? = null) : MutableList<EService>{
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

    fun returnSearchList(searchList : ArrayList<EService> , editTextKey : EditText , adapter : GroupAdapter<GroupieViewHolder>,imgCancelBtn :ImageView , context : Context){
    adapter.clear()
   val myFilterSearchList = searchList.filter {
           it.title.contains(editTextKey.text.toString().trim(),ignoreCase = true)
        }
        if(editTextKey.text.toString().trim().isNullOrEmpty()) {
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
        }else{
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