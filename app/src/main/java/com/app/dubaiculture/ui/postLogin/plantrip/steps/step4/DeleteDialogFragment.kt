package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentDeleteDialogBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment

class DeleteDialogFragment : BaseBottomSheetFragment<FragmentDeleteDialogBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDeleteDialogBinding.inflate(inflater, container, false)
}