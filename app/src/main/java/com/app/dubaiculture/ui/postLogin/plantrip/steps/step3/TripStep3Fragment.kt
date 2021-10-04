package com.app.dubaiculture.ui.postLogin.plantrip.steps.step3

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentTripStep2Binding
import com.app.dubaiculture.databinding.FragmentTripStep3Binding
import com.app.dubaiculture.ui.base.BaseFragment

class TripStep3Fragment: BaseFragment<FragmentTripStep3Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentTripStep3Binding.inflate(inflater, container, false)
}