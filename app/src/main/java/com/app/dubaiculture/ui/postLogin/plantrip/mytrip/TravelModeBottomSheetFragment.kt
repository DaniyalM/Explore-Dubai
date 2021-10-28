package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentTravelModeBottomSheetBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment

class TravelModeBottomSheetFragment :
    BaseBottomSheetFragment<FragmentTravelModeBottomSheetBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTravelModeBottomSheetBinding.inflate(inflater, container, false)
}