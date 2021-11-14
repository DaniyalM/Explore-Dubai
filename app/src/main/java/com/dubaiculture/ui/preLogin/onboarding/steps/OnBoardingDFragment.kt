package com.dubaiculture.ui.preLogin.onboarding.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentOnBoardingDBinding
import com.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingDFragment : BaseFragment<FragmentOnBoardingDBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnBoardingDBinding.inflate(inflater, container, false)

}