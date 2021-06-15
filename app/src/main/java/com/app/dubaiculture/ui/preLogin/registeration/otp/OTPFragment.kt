package com.app.dubaiculture.ui.preLogin.registeration.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentOTPBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.explore.ExploreActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.ui.preLogin.registeration.otp.viewmodel.OTPViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.COMES_FROM_LOGIN
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OTPFragment : BaseBottomSheetFragment<FragmentOTPBinding>(), View.OnClickListener{
    private val otpViewModel: OTPViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private var verificationCode: String? = null
    private var emailOrPhoneLogin : String?=null
    private var passwordlogin : String?=null
    private var from: String? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.fragment = this
        binding.viewmodel = otpViewModel
        arguments?.let {
            verificationCode = it.getString("verificationCode")
            from = it.getString("screen_name")
            emailOrPhoneLogin = it.getString("emailorphone")
            passwordlogin = it.getString("password")
        }

        subscribeUiEvents(otpViewModel)

        isArabic()
        Timber.e(verificationCode)

        binding.btnContinueReg.setOnClickListener(this)
        binding.tvResend.setOnClickListener(this)
        disabledBackButton()
        loginWithConfirmOTPObserver()
        if(from=="registerFragment"){
            isCancelable = false
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentOTPBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_continue_reg -> {
                when (from) {
                    "forgotfragment" -> {
                        otpViewModel.validateOTP(
                            verificationCode!!,
                            binding.otpView.text.toString().trim()
                        )
                    }
                    COMES_FROM_LOGIN -> {
                        otpViewModel.confirmLoginOTP(verificationCode.toString(),binding.otpView.text.toString())
                    }
                    else -> verificationCode?.let {
                        otpViewModel.confirmOTP(it, binding.otpView.text.toString())
                    }
                }
            }
            R.id.tvResend -> {
                verificationCode?.let { otpViewModel.resendOTP(it) }
            }
        }
    }
private fun disabledBackButton(){
    val callback: OnBackPressedCallback = object : OnBackPressedCallback(false /* enabled by default */) {
        override fun handleOnBackPressed() {
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}
    private fun loginWithConfirmOTPObserver(){
        otpViewModel.loginConfirmOTPStatus.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { it->
                if(it){
                    emailOrPhoneLogin?.let { data ->
                        if(data.startsWith("+"))
                        {
                            loginViewModel.loginWithPhone(emailOrPhoneLogin,passwordlogin)
                        }else{
                            loginViewModel.loginWithEmail(emailOrPhoneLogin,passwordlogin)

                        }
                    }
                }
            }
        }

        loginViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it != null) {

                application.auth.isLoggedIn = true
                application.auth.isGuest = false
                activity.killSessionAndStartNewActivity(ExploreActivity::class.java)

            }
        }
//        loginViewModel.loginStatus.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let {
//                application.auth.isGuest=false
//                activity.killSessionAndStartNewActivity(ExploreActivity::class.java)
//            }
//        }
    }
}


