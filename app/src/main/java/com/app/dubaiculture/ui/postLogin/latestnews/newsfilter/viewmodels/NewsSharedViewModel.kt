package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsSharedViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application) {




}