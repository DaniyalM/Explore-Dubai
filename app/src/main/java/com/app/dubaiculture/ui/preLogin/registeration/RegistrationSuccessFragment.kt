package com.app.dubaiculture.ui.preLogin.registeration

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentCreatePassBinding
import com.app.dubaiculture.databinding.FragmentRegisterationSuccessBinding
import com.app.dubaiculture.ui.base.BaseFragment


class RegistrationSuccessFragment : BaseFragment<FragmentRegisterationSuccessBinding>(){


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentRegisterationSuccessBinding.inflate(inflater,container,false)

}