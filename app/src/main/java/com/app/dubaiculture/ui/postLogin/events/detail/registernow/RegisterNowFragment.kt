package com.app.dubaiculture.ui.postLogin.events.detail.registernow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentRegisterNowBinding
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterNowFragment : BaseFragment<FragmentRegisterNowBinding>() , View.OnClickListener{

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding!!.btnSubmit.setOnClickListener(this)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentRegisterNowBinding.inflate(inflater,container,false)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_submit->{
                val bundle = bundleOf(
                    "key" to "RegisterNow"
                )
                navigate(R.id.action_registerNowFragment_to_registrationSuccessFragment2, bundle)
            }
        }
    }
}