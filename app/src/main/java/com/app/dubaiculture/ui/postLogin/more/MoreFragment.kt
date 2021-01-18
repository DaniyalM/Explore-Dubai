package com.app.dubaiculture.ui.postLogin.more

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentMoreBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment

class MoreFragment : BaseBottomSheetFragment<FragmentMoreBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoreBinding.inflate(inflater, container, false)
}