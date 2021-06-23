package com.app.dubaiculture.ui.preLogin.onboarding.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentOnBoardingCBinding
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingCFragment : BaseFragment<FragmentOnBoardingCBinding>(), View.OnClickListener {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSkip.setOnClickListener(this)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnBoardingCBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_skip->{

            }
        }
    }
}