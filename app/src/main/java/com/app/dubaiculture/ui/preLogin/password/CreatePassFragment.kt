package com.app.dubaiculture.ui.preLogin.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentCreatePassBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.password.bottomsheet.PasswordUpdatedFragment

class CreatePassFragment : BaseFragment<FragmentCreatePassBinding>(),View.OnClickListener{
    private var modalDismissWithAnimation = false
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnSetPass.btn.setOnClickListener { showModalBottomSheet() }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCreatePassBinding.inflate(inflater,container,false)


    private fun showModalBottomSheet() {
        val modalBottomSheet = PasswordUpdatedFragment.newInstance(modalDismissWithAnimation)
        modalBottomSheet.show(requireActivity().supportFragmentManager, PasswordUpdatedFragment.TAG)
    }

    override fun onClick(v: View?) {
        when(v?.id){
        }

    }
}