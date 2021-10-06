package com.app.dubaiculture.ui.postLogin.plantrip.steps.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentTripStep1Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.app.dubaiculture.ui.postLogin.plantrip.steps.PlanTripParentFragment

class TripStep1Fragment : BaseFragment<FragmentTripStep1Binding>() {

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
    }

    fun onNextClicked() {

        customNavigation.navigateStep(true,R.id.tripStep1)

    }

}