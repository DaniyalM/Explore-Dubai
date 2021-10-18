package com.app.dubaiculture.ui.postLogin.plantrip.steps.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentTripStep1Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.app.dubaiculture.ui.postLogin.plantrip.steps.PlanTripParentFragment
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.Step1ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripStep1Fragment : BaseFragment<FragmentTripStep1Binding>() {

    private val step1ViewModel: Step1ViewModel by viewModels()

    companion object{
        lateinit var customNavigation:CustomNavigation
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep1Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        lottieAnimationRTL(binding.animationView)
        subscribeToObservables()


    }

    private fun subscribeToObservables() {
        step1ViewModel.userType.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {

            }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()

    }

    fun onNextClicked() {

        customNavigation.navigateStep(true,R.id.tripStep1)

    }

}