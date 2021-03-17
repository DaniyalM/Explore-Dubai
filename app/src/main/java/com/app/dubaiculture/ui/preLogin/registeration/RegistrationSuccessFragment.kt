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


class RegistrationSuccessFragment : BaseFragment<FragmentRegisterationSuccessBinding>(), View.OnClickListener{
    private var from : String? = ""
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            from =    it.getString("key")
        }

        if(from == "RegisterNow"){
            binding.regSuccessful.text = getString(R.string.register_confirm)
        }
        binding.btnContinueReg.setOnClickListener(this)

        binding.imgClose.setOnClickListener(this)
        lottieAnimationRTL(binding.animRegistration)
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentRegisterationSuccessBinding.inflate(inflater,container,false)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_continue_reg->{
                if(from == "RegisterNow"){
                    back()
                }else
                navigate(R.id.action_registrationSuccessFragment_to_loginFragment)
            }
            R.id.img_close->{
                back()
            }
        }
    }
}