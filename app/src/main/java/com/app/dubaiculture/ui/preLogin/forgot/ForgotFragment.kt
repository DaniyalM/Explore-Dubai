package com.app.dubaiculture.ui.preLogin.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentForgotBinding
import com.app.dubaiculture.ui.base.BaseFragment

class ForgotFragment : BaseFragment<FragmentForgotBinding>(),View.OnClickListener{

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