package com.app.dubaiculture.ui.postLogin.plantrip.steps.step1

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentTripStep1Binding
import com.app.dubaiculture.ui.base.BaseFragment

class TripStep1Fragment : BaseFragment<FragmentTripStep1Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep1Binding.inflate(inflater, container, false)
}