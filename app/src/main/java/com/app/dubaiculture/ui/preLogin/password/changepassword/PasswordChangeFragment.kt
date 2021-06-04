package com.app.dubaiculture.ui.preLogin.password.changepassword

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentPasswordChangeBinding
import com.app.dubaiculture.ui.base.BaseFragment

class PasswordChangeFragment : BaseFragment<FragmentPasswordChangeBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPasswordChangeBinding.inflate(inflater, container, false)

}