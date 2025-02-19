package com.dubaiculture.ui.preLogin.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentForgotBinding
import com.dubaiculture.databinding.FragmentRegisterBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.preLogin.forgot.viewModel.ForgotViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : BaseFragment<FragmentForgotBinding>(),View.OnClickListener{
    private val forgotViewModel: ForgotViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgotBinding {
        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds).setDuration(500)
        sharedElementReturnTransition =  TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds).setDuration(500)
        return FragmentForgotBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding!!.viewmodel = forgotViewModel
        lottieAnimationRTL(binding!!.animationView)
        subscribeUiEvents(forgotViewModel)
        backArrowRTL(binding!!.imgClose)
        viewInitialize()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close-> back()
            R.id.tv_cancel -> back()
            R.id.btn_otp -> {
                val bundle = bundleOf(
                    "key" to  "ForgotFragment",
                )
                navigate(R.id.action_forgotFragment_to_bottomSheet,bundle)
            }
        }
    }


    private fun viewInitialize(){
        binding!!.imgClose.setOnClickListener(this)
        binding!!.tvCancel.setOnClickListener(this)
        binding!!.btnOtp.setOnClickListener(this)

    }
}