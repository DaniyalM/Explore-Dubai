package com.dubaiculture.ui.preLogin.password.passwordupdated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentPasswordUpdatedBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment

class PasswordUpdatedFragment : BaseBottomSheetFragment<FragmentPasswordUpdatedBinding>(),
    View.OnClickListener {

    var from: String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener(this)
        arguments?.let {
            from = it.getString("post")
        }

        isCancelable = false
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPasswordUpdatedBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_continue -> {
                if (from == "postFragment") {
//                    findNavController().navigate(R.id.action_passwordUpdatedFragment_to_loginFragment)
                    findNavController().navigate(R.id.action_passwordUpdatedFragment2_to_exploreFragment)


                } else {
                    findNavController().navigate(R.id.action_passwordUpdatedFragment_to_loginFragment)
                }
                dismiss()
            }

        }
    }

}