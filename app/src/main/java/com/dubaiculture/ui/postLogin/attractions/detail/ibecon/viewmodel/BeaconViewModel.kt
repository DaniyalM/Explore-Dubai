package com.dubaiculture.ui.postLogin.attractions.detail.ibecon.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.sitemap.SiteMapRespository
import com.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.Constants.NavBundles.ATTRACTION_DETAIL_BEACON
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeaconViewModel @Inject constructor(
    application: Application,
    private val siteMapRespository: SiteMapRespository,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(application) {
    private val context = getApplication<ApplicationEntry>()


    private val _beaconList: MutableLiveData<Event<List<BeaconItems>>> = MutableLiveData()
    val beaconList: LiveData<Event<List<BeaconItems>>> = _beaconList

    init {
        savedStateHandle.get<String>(ATTRACTION_DETAIL_BEACON)?.let {
            siteMap(it, context.auth.locale.toString())
        }
    }

    fun refreshList() {
        savedStateHandle.get<String>(ATTRACTION_DETAIL_BEACON)?.let {
            siteMap(it, context.auth.locale.toString())
        }
    }


    fun siteMap(id: String, locale: String) {
        viewModelScope.launch {
            showLoader(true)
            when (val result =
                siteMapRespository.fetchSiteMap(SiteMapRequest(id = id, culture = locale))) {
                is Result.Success -> {
                    showLoader(false)

                    if (!result.value.ibeconItems.isNullOrEmpty()) {
                        _beaconList.value = Event(result.value.ibeconItems)
                    }
                }
                is Result.Failure -> {
                    showLoader(false)

                }


            }
        }
    }


}