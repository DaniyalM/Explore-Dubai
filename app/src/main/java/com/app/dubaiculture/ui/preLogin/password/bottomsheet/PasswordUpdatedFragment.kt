package com.app.dubaiculture.ui.preLogin.password.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentPasswordUpdatedBinding
import com.app.dubaiculture.ui.base.BaseFragment

class PasswordUpdatedFragment() : BaseFragment<FragmentPasswordUpdatedBinding>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentPasswordUpdatedBinding.inflate(inflater,container,false)




}