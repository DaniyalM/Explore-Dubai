package com.app.dubaiculture.ui.postLogin.plantrip.steps.step2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentTripStep1Binding
import com.app.dubaiculture.databinding.FragmentTripStep2Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripStep2Fragment:BaseFragment<FragmentTripStep2Binding>() {

    companion object{
        lateinit var customNavigation: CustomNavigation
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep2Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        lottieAnimationRTL(binding.animationView)

    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()

    }

    fun onPreviousClicked(){
        customNavigation.navigateStep(false, R.id.tripStep2)
    }

    fun onNextClicked(){
        customNavigation.navigateStep(true, R.id.tripStep2)
    }

}