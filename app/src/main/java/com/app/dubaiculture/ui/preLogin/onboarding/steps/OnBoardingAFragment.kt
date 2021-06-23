package com.app.dubaiculture.ui.preLogin.onboarding.steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentOnBoardingABinding
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingAFragment : BaseFragment<FragmentOnBoardingABinding>(), View.OnClickListener {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSkip.setOnClickListener(this)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentOnBoardingABinding.inflate(inflater,container,false)

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.btn_skip->{

           }
       }
    }
}