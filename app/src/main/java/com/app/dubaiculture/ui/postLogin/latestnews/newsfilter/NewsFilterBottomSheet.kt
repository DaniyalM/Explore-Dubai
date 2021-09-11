package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.app.dubaiculture.databinding.FragmentFilterBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewsSharedViewModel

class NewsFilterBottomSheet : BaseBottomSheetFragment<FragmentFilterBinding>() {
    private val newsFilterViewModel:NewsSharedViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFilterBinding.inflate(inflater, container, false)
}