package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.DialogMyTripNameBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment

class MyTripNameDialog : BaseBottomSheetFragment<DialogMyTripNameBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogMyTripNameBinding.inflate(inflater, container, false)
}