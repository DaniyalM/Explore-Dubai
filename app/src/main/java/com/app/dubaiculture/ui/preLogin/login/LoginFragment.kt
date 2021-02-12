package com.app.dubaiculture.ui.preLogin.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLoginBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener{

    private val loginViewModel: LoginViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = loginViewModel
        subscribeUiEvents(loginViewModel)
        binding.fragment = this
        binding.forgotPass.setOnClickListener(this)
        binding.imgUaePass.setOnClickListener(this)
        lottieAnimationRTL(binding.animationView)
        binding.tvRegisterNow.setOnClickListener {
            findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment2)
        }
        binding.tvAsGuest.setOnClickListener {
            application.auth.isLoggedIn = true
            activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
        }
        binding.languageSwitch.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            if (b)
                setLanguage(Locale( "ar"))
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
        loginViewModel.oneError.observe(viewLifecycleOwner){
            if(it==true){
                binding.tvPhoneError.text = resources.getString(R.string.err_phone_two)
            }
        }
        loginViewModel.twoError.observe(viewLifecycleOwner){
            if(it==true){
                binding.tvPhoneError.text = resources.getString(R.string.err_phone)
            }else{
                binding.tvPhoneError.text =""
            }
        }

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
            R.id.forgot_pass ->
//                loginViewModel.showAlert(message = "Hello")
                navigate(R.id.action_loginFragment_to_forgotFragment)

//                navigate(R.id.action_loginFragment_to_bottomSheet)


            R.id.img_uae_pass -> {
            }
        }
    }
}