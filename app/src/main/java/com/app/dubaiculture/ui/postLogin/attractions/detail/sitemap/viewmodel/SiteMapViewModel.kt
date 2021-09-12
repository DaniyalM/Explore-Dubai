package com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.sitemap.SiteMapRespository
import com.app.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.app.dubaiculture.data.repository.sitemap.remote.request.SiteMapRequest
import com.app.dubaiculture.ui.base.BaseViewModel
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


    fun siteMap(id: String, locale: String) {
        viewModelScope.launch {
            showLoader(true)
            when (val result =
                siteMapRespository.fetchSiteMap(SiteMapRequest(id = id, culture = locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _siteMapData.value = result.value
                }
                is Result.Failure -> {
                    showLoader(false)

                }


            }
        }
    }

}