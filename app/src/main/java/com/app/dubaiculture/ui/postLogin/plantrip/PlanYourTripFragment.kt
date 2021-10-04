package com.app.dubaiculture.ui.postLogin.plantrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentAttractionListingBinding
import com.app.dubaiculture.databinding.FragmentPlanYourTripBinding
import com.app.dubaiculture.ui.base.BaseFragment

class PlanYourTripFragment : BaseFragment<FragmentPlanYourTripBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlanYourTripBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    fun onBottomSheetClicked() {

        navigate(R.id.action_tripFragment_to_tripDetailParentFragment)


    }

    fun onBackPressed() {
        back()
    }

}