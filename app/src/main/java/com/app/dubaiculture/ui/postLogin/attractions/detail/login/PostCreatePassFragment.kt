package com.app.dubaiculture.ui.postLogin.attractions.detail.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentForgotBinding
import com.app.dubaiculture.databinding.FragmentPostCreatePassBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.login.viewmodel.PostCreatePassViewModel
import com.app.dubaiculture.ui.preLogin.password.viewModel.CreatePassViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostCreatePassFragment : BaseFragment<FragmentPostCreatePassBinding>(),View.OnClickListener {
    private val postCreatePassViewModel: PostCreatePassViewModel by viewModels()
    private var verificationCode: String? = null
    private var from: String? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentPostCreatePassBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            verificationCode = it.getString("verificationCode")
            from = it.getString("post")
        }
        binding.btnSetPassword.setOnClickListener {
            postCreatePassViewModel.setPassword(verificationCode,from)
        }
        binding.imgClose.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close-> back()
        }
    }

}