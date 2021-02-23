package com.app.dubaiculture.ui.preLogin.login

import android.app.Instrumentation
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLoginBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = loginViewModel
        subscribeUiEvents(loginViewModel)
        binding.fragment = this
        binding.forgotPass.setOnClickListener(this)
        binding.imgUaePass.setOnClickListener(this)
        lottieAnimationRTL(binding.animationView)
        binding.tvRegisterNow.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.password to "my_password",
                binding.editPassword to "my_edit_password",
                binding.mobileNumber to "my_phone",
                binding.editMobNo to "my_edit_phone",
                binding.tvLoginAccount to "main_title",
                binding.btnLogin to "action_btn"
            )
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2,
                null,
                null,
                extras)
        }
        binding.tvAsGuest.setOnClickListener {
            application.auth.isLoggedIn = true
            activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
        }
        binding.languageSwitch.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if (b)
                setLanguage(Locale("ar"))
            else setLanguage(Locale.ENGLISH)
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    activity.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        loginViewModel.loginStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.forgot_pass ->{
                val extras =FragmentNavigatorExtras(
                    binding.password to "my_password",
                    binding.editPassword to "my_edit_password",
                    binding.mobileNumber to "my_phone",
                    binding.editMobNo to "my_edit_phone",
                    binding.tvLoginAccount to "main_title",
                    binding.btnLogin to "action_btn"
            )
                findNavController().navigate(R.id.action_loginFragment_to_forgotFragment,
                    null,
                    null,
                    extras)
            }
            R.id.img_uae_pass -> {
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.e("Called Pause")

    }
    override fun onDestroy() {
        super.onDestroy()
        Timber.e("Called destroy")
    }
}
