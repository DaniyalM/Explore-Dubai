package com.app.dubaiculture.ui.preLogin.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentForgotBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.forgot.viewModel.ForgotViewModel
import com.app.dubaiculture.ui.preLogin.password.viewModel.CreatePassViewModel

class ForgotFragment : BaseFragment<FragmentForgotBinding>(),View.OnClickListener{
    private val forgotViewModel: ForgotViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentForgotBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewInitialize()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close-> back()
        }
    }


    private fun viewInitialize(){
        binding.imgClose.setOnClickListener(this)
    }
}