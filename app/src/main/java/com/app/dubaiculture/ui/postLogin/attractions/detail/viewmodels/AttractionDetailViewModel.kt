package com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import javax.inject.Inject

class AttractionDetailViewModel @ViewModelInject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
): BaseViewModel(application) {
}