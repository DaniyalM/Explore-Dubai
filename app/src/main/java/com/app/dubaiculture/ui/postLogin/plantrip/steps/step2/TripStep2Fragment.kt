package com.app.dubaiculture.ui.postLogin.plantrip.steps.step2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentTripStep1Binding
import com.app.dubaiculture.databinding.FragmentTripStep2Binding
import com.app.dubaiculture.ui.base.BaseFragment

class TripStep2Fragment:BaseFragment<FragmentTripStep2Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep2Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    fun onPreviousClicked(){
        back()
    }

}