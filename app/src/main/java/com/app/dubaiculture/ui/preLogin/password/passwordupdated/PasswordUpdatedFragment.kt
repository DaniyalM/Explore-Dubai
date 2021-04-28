package com.app.dubaiculture.ui.preLogin.password.passwordupdated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPasswordUpdatedBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class PasswordUpdatedFragment : BaseBottomSheetFragment<FragmentPasswordUpdatedBinding>(), View.OnClickListener {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnContinue.setOnClickListener(this)
        isCancelable =false
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentPasswordUpdatedBinding.inflate(inflater,container,false)

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_continue->{
                findNavController().navigate(R.id.action_passwordUpdatedFragment_to_loginFragment)
                dismiss()
            }

        }
    }

}