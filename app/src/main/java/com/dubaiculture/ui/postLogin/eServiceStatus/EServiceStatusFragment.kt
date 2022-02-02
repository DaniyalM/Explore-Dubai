package com.dubaiculture.ui.postLogin.eServiceStatus

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dubaiculture.databinding.FragmentEServiceStatusBinding
import com.dubaiculture.ui.base.BaseFragment

class EServiceStatusFragment : BaseFragment<FragmentEServiceStatusBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEServiceStatusBinding =
        FragmentEServiceStatusBinding.inflate(inflater, container, false)

}