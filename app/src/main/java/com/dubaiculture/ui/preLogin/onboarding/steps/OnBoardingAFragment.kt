package com.dubaiculture.ui.preLogin.onboarding.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubaiculture.databinding.FragmentOnBoardingABinding
import com.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingAFragment : BaseFragment<FragmentOnBoardingABinding>(), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.animLottie.imageAssetsFolder = "images/"
//        binding.animLottie.setAnimation("onboarding_one.json")
//        binding.animLottie.playAnimation("onboarding_one.json")

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnBoardingABinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {
//           R.id.btn_skip->{
//
//           }
        }
    }
}