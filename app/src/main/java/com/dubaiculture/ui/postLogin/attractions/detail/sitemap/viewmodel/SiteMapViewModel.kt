package com.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.sitemap.SiteMapRespository
import com.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequest
import com.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SiteMapViewModel @Inject constructor(
    application: Application, private val siteMapRespository: SiteMapRespository,
) :
    BaseViewModel(application) {

    private val _siteMapData: MutableLiveData<SiteMapModel> = MutableLiveData()
    val siteMapData: LiveData<SiteMapModel> = _siteMapData

    val _beacons: MutableLiveData<List<BeaconItems>> = MutableLiveData()
    val beacons: LiveData<List<BeaconItems>> = _beacons



    fun siteMap(id: String, locale: String) {
        viewModelScope.launch {
            showLoader(true)
            when (val result =
                siteMapRespository.fetchSiteMap(SiteMapRequest(id = id, culture = locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _siteMapData.value = result.value

                    result.value.ibeconItems.filter { it.proximityID.isNotEmpty()&& it.minor.isNotEmpty() && it.major.isNotEmpty() }.let {
                        _beacons.value=it
                    }

                }
                is Result.Failure -> {
                    showLoader(false)

                }


            }
        }
    }




    fun updateBeacons(){
        val data = _beacons.value ?: return
        data.filter {
            !it.visited
        }.let {
            _beacons.value
        }
    }

}