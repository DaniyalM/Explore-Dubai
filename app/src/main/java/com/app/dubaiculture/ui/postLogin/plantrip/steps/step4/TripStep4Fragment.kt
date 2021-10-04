package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentTripStep3Binding
import com.app.dubaiculture.databinding.FragmentTripStep4Binding
import com.app.dubaiculture.ui.base.BaseFragment

class TripStep4Fragment: BaseFragment<FragmentTripStep4Binding>()  {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentTripStep4Binding.inflate(inflater, container, false)
}