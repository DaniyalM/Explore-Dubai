package com.app.dubaiculture.ui.postLogin.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPostForgotBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.login.viewmodel.PostForgotViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostForgotFragment : BaseFragment<FragmentPostForgotBinding>(),View.OnClickListener {
    private val postForgotViewModel: PostForgotViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentPostForgotBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding!!.viewmodel = postForgotViewModel
        lottieAnimationRTL(binding!!.animationView)
        subscribeUiEvents(postForgotViewModel)
        backArrowRTL(binding!!.imgClose)
        viewInitialize()
    }
    private fun viewInitialize(){
        binding.imgClose.setOnClickListener(this)
        binding.tvCancel.setOnClickListener(this)
        binding.btnOtp.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close-> back()
            R.id.tv_cancel -> back()
            R.id.btn_otp -> {
                val bundle = bundleOf(
                    "key" to  "ForgotFragment",
                )
                navigate(R.id.action_postForgotFragment_to_postOTPDialogFragment,bundle)
            }
        }
    }
}