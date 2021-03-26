package com.app.dubaiculture.ui.postLogin.attractions.detail.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPostLoginBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostLoginFragment : BaseBottomSheetFragment<FragmentPostLoginBinding>() , View.OnClickListener{
    private val loginViewModel: LoginViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentPostLoginBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = loginViewModel
        binding.btnLogin.setOnClickListener(this)
        binding.imgUaePass.setOnClickListener(this)
        binding.tvRegisterNow.setOnClickListener(this)
        binding.tvForgotPass.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login->{
                dismiss()
            }
            R.id.img_uae_pass->{
            }
            R.id.tv_register_now->{
                navigate(R.id.action_postLoginFragment_to_registerFragment)
            }
            R.id.tv_forgot_pass->{
                navigate(R.id.action_postLoginFragment_to_forgotFragment2)
            }
        }
    }

}