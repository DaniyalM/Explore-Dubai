package com.dubaiculture.ui.preLogin.onboarding.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentOnBoardingBBinding
import com.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class OnBoardingBFragment : BaseFragment<FragmentOnBoardingBBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnBoardingBBinding.inflate(inflater, container, false)

}