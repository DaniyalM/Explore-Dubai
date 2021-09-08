package com.app.dubaiculture.ui.postLogin.attractions.detail.ar.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.viewgallery.ViewGalleryRepository
import com.app.dubaiculture.data.repository.viewgallery.local.ViewGalleryModel
import com.app.dubaiculture.data.repository.viewgallery.remote.request.ViewGalleryRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ARDetailViewModel @Inject constructor(
    private val viewGalleryRepository: ViewGalleryRepository,
    application: Application
) : BaseViewModel(application) {

    private val _metaDataAr: MutableLiveData<ViewGalleryModel> = MutableLiveData()
    val metaDataAr: LiveData<ViewGalleryModel> = _metaDataAr


    fun getMetaDataAr(id: String, locale: String) {
        viewModelScope.launch {
            showLoader(true)
            when (val result = viewGalleryRepository.fetchMetaData(
                ViewGalleryRequest(
                    id = id,
                    culture = locale
                )
            )) {
                is com.app.dubaiculture.data.Result.Success -> {
                    showLoader(false)
                    _metaDataAr.value = result.value
                }
                is com.app.dubaiculture.data.Result.Failure -> {
                    showLoader(false)
                    when {
                        result.errorCode == 401 -> {
                            showToast("Server Error")
                        }
                        result.isNetWorkError -> {
                            showErrorDialog(
                                message = INTERNET_CONNECTION_ERROR,
                                colorBg = R.color.red_600
                            )
                        }
                        else -> {
                            showToast("Server Error")
                        }
                    }

                }
            }
            showLoader(false)
        }
    }
}