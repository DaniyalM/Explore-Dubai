package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentNoDaysBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment

class NoOfDaysFragment : BaseBottomSheetFragment<FragmentNoDaysBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoDaysBinding.inflate(inflater, container, false)


}