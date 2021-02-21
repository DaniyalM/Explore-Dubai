package com.app.dubaiculture.ui.preLogin.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentCreatePassBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.password.passwordupdated.PasswordUpdatedFragment
import com.app.dubaiculture.ui.preLogin.password.viewModel.CreatePassViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_pass.view.*
@AndroidEntryPoint
class CreatePassFragment : BaseFragment<FragmentCreatePassBinding>(),View.OnClickListener{
    private val createPassViewModel: CreatePassViewModel by viewModels()
    private var verificationCode : String?= null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = createPassViewModel
        lottieAnimationRTL(binding.animationView)
        subscribeUiEvents(createPassViewModel)
        backArrowRTL(binding.imgClose)
        arguments?.let {             verificationCode = it.getString("verificationCode") }
        binding.btnSetPassword.setOnClickListener {
            createPassViewModel.setPassword(verificationCode)
        }
        binding.imgClose.setOnClickListener(this)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCreatePassBinding.inflate(inflater,container,false)



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close-> back()
        }

    }
}