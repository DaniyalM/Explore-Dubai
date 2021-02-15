package com.app.dubaiculture.ui.preLogin.login

import android.os.Bundle
import android.view.*
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
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.security.auth.callback.Callback


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener{

    private val loginViewModel: LoginViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       val phone =  PhoneNumberUtil.getInstance()
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
      //  errorMessage()
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
                navigate(R.id.action_loginFragment_to_forgotFragment)
            R.id.img_uae_pass -> {
            }
        }
    }

}
