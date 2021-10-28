package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentMyTripListingBinding
import com.app.dubaiculture.ui.base.BaseFragment

class MyTripListingFragment : BaseFragment<FragmentMyTripListingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyTripListingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    fun onBackPressed() {
        navigateBack()
//    navigateByDirections(MyTripFragmentDirections.actionTripFragmentToTripDetailParentFragment())
    }

    fun onSaveTripClicked() {
        navigate(R.id.action_myTrip_to_myTripNameDialog)
    }
}