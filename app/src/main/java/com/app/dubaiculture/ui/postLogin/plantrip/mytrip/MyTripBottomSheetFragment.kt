package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentMyTripBottomsheetBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment

class MyTripBottomSheetFragment : BaseBottomSheetFragment<FragmentMyTripBottomsheetBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyTripBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    fun onBottomSheetClicked() {

        navigate(R.id.action_my_trip_to_my_trip_listing)

    }

    fun onSaveTripClicked() {
        navigate(R.id.action_myTrip_bottom_sheet_to_myTripNameDialog)
    }

    fun onTravelModeClicked() {
        navigate(R.id.action_my_trip_to_travel_mode_dialog)

    }

}