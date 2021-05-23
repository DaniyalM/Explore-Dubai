package com.app.dubaiculture.ui.preLogin.login

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLoginBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.firebase.getFcmToken
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import com.estimote.coresdk.common.requirements.SystemRequirementsHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = loginViewModel
        subscribeUiEvents(loginViewModel)
        binding.fragment = this
        binding.forgotPass.setOnClickListener(this)
        binding.imgUaePass.setOnClickListener(this)
//        SystemRequirementsChecker.Requirement.LOCATION_DISABLED


        lottieAnimationRTL(binding.animationView)
        applicationExitDialog()
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
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment2,
                null,
                null,
                extras
            )
        }
        binding.tvAsGuest.setOnClickListener {
        if(SystemRequirementsHelper.isLocationServiceForBluetoothLeEnabled(requireContext()) && SystemRequirementsHelper.isBluetoothEnabled(requireContext())){
            loginViewModel.getUserIfExists()
            application.auth.apply {
                isLoggedIn = true
                isGuest = true
            }
            activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
        }else if(!SystemRequirementsHelper.isBluetoothEnabled(requireContext())){
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        }else{
            SystemRequirementsChecker.checkWithDefaultDialogs(requireActivity())
        }
        }
        binding.languageSwitch.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if (b)
                setLanguage(Locale("ar"))
            else setLanguage(Locale.ENGLISH)
        }



        subscribeToObservables()
        if (isArabic()) {
            binding.imgUaePass.setImageResource(R.drawable.uae_pass_new)
        } else {
            binding.imgUaePass.setImageResource(R.drawable.uae_pass)
        }
        lifecycleScope.launch {
            Timber.e("Token: ${getFcmToken()}")
        }
    }


    private fun subscribeToObservables() {
        loginViewModel.loginStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                application.auth.isGuest = false
                activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
            }
        }
        loginViewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                loginViewModel.removeUser(it)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.forgot_pass -> {
                val extras = FragmentNavigatorExtras(
                    binding.password to "my_password",
                    binding.editPassword to "my_edit_password",
                    binding.mobileNumber to "my_phone",
                    binding.editMobNo to "my_edit_phone",
                    binding.tvLoginAccount to "main_title",
                    binding.btnLogin to "action_btn"
                )
                findNavController().navigate(
                    R.id.action_loginFragment_to_forgotFragment,
                    null,
                    null,
                    extras
                )
            }
            R.id.img_uae_pass -> {
//                navigate(R.id.action_loginFragment_to_eventNearMapFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        loginViewModel.isPassword.value = true
        loginViewModel.isPhone.value = true
        loginViewModel.isPhoneEdit.value = true
        loginViewModel.isEmailEdit.value = true
        loginViewModel.isEmail.value = true
    }
    private fun applicationExitDialog() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    showAlert(
                        message = getString(R.string.error_msg),
                        textPositive = getString(R.string.okay),
                        textNegative = getString(R.string.cancel),
                        actionNegative = {
                        },
                        actionPositive = {
                            activity.finish()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}
