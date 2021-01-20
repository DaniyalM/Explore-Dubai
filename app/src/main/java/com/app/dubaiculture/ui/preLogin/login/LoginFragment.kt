package com.app.dubaiculture.ui.preLogin.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLoginBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint
import net.bytebuddy.asm.Advice
import java.util.*


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = loginViewModel
        binding.fragment = this
        subscribeUiEvents(loginViewModel)
        subscribeToObservables()

        binding.registration.setOnClickListener {
            findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment2)

        }
        binding.forgotPassword.setOnClickListener {
            loginViewModel.showToast("ForgotPassword!")
        }
    }


    private fun subscribeToObservables() {
        application.auth.isLoggedIn = false
        loginViewModel.loginStatus.observe(viewLifecycleOwner) {
            if (it) {
                application.auth.isLoggedIn = true
                activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
            }
        }

        loginViewModel.emailStatus.observe(viewLifecycleOwner) {
            binding.emailLayout.isErrorEnabled = false
            if (it) {
                binding.emailLayout.isErrorEnabled = true
                binding.emailLayout.error = "Invalid Email."
            }
        }

        loginViewModel.passwordStatus.observe(viewLifecycleOwner) {
            binding.passWordLayout.isErrorEnabled = false
            if (it) {
                binding.passWordLayout.isErrorEnabled = true
                binding.passWordLayout.error = "Invalid Password."
            }
        }
    }
    fun changeLocalIntoAr(){
        setLanguage(Locale("ar"))
    }
}