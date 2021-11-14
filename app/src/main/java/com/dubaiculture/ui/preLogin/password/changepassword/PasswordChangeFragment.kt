package com.dubaiculture.ui.preLogin.password.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.databinding.FragmentPasswordChangeBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.preLogin.password.changepassword.viewmodel.ChangedPasswordViewModel
import com.dubaiculture.ui.preLogin.password.viewModel.CreatePassViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordChangeFragment : BaseFragment<FragmentPasswordChangeBinding>() {
    private val changedPasswordViewModel: ChangedPasswordViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPasswordChangeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backArrowRTL(binding.header.back)
        lottieAnimationRTL(binding.animationView)
        subscribeUiEvents(changedPasswordViewModel)
        binding.viewmodel = changedPasswordViewModel
        binding.header.back.setOnClickListener {
            back()
        }
        callingObserver()
    }
    private fun callingObserver(){
        changedPasswordViewModel.changedPasswordStatus.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                if(it == true){
                    back()
                }
            }
        }
    }
}