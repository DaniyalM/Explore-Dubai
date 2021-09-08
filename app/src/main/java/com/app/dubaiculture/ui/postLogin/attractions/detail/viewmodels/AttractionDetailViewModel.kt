package com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels

import android.app.Application
import com.app.dubaiculture.data.repository.attraction.AttractionRepository
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttractionDetailViewModel @Inject constructor(
    application: Application,
    private val attractionRepository: AttractionRepository,
) : BaseViewModel(application, attractionRepository) {
}