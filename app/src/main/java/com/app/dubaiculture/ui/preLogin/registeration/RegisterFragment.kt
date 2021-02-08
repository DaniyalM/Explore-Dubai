package com.app.dubaiculture.ui.preLogin.registeration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentRegisterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.registeration.bottomsheet.OTPFragment
import com.app.dubaiculture.ui.preLogin.registeration.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), View.OnClickListener {
    private val registrationViewModel: RegistrationViewModel by viewModels()
    private var modalDismissWithAnimation = false

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRegisterBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(registrationViewModel)
        binding.btnRegister.setOnClickListener(this)
        binding.tvLoginNow.setOnClickListener(this)
        binding.imgClose.setOnClickListener(this)

        binding.viewmodel = registrationViewModel

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                navigate(R.id.action_registerFragment2_to_bottomSheet)
            }
            R.id.tv_login_now -> {
                back()
            }
            R.id.img_close -> {
                back()
            }

        }
    }

    private fun showModalOTPlBottomSheet() {
        val modalBottomSheet = OTPFragment.newInstance(modalDismissWithAnimation)
        modalBottomSheet.show(requireActivity().supportFragmentManager, OTPFragment.TAG)
    }

}