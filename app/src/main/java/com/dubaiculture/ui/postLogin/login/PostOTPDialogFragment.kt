package com.dubaiculture.ui.postLogin.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.broadcasts.OTPbroadCastReceiver
import com.dubaiculture.databinding.PostOtpFragmentDialogBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.PostLoginActivity
import com.dubaiculture.ui.postLogin.login.viewmodel.PostOTPViewModel
import com.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.NavBundles.COMES_FROM_POST_LOGIN
import com.dubaiculture.utils.killSessionAndStartNewActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class PostOTPDialogFragment : BaseBottomSheetFragment<PostOtpFragmentDialogBinding>(),
    View.OnClickListener {
    private val otpViewModel: PostOTPViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private var verificationCode: String? = null
    private var emailOrPhoneLogin: String? = null
    private var passwordlogin: String? = null
    private var from: String? = null


    //    private val REQ_USER_CONSENT = 200
    var smsBroadCastReceiver: OTPbroadCastReceiver? = null


    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val message = result.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
            getOtpFromMessage(message)
        }

    }

    private fun getOtpFromMessage(message: String?) {
        val otpPattern = Pattern.compile("(|^)\\d{4}")
        val matcher = otpPattern.matcher(message)
        if (matcher.find()) {
            binding.otpView.setText(matcher.group(0))
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        receiveBroadCastReceiver()
    }

    override fun onDetach() {
        super.onDetach()
        activity.unregisterReceiver(smsBroadCastReceiver)
    }

    private fun receiveBroadCastReceiver() {
        smsBroadCastReceiver = OTPbroadCastReceiver()
        smsBroadCastReceiver!!.smsBroadCastReceiverListener =
            object : OTPbroadCastReceiver.SmsBroadCastReceiverListener {
                override fun onSuccess(intent: Intent) {
                    startForResult.launch(intent)

//                    startActivityForResult(intent, REQ_USER_CONSENT)
                }

                override fun onFailure() {
                }


            }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        activity.registerReceiver(smsBroadCastReceiver, intentFilter)
    }


    private fun smartUserConsent() {
        val client = SmsRetriever.getClient(activity)
        client.startSmsUserConsent(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.viewmodel = otpViewModel
        arguments?.let {
            verificationCode = it.getString("verificationCode")
            from = it.getString("screen_name")
            emailOrPhoneLogin = it.getString("emailorphone")
            passwordlogin = it.getString("password")
        }
        subscribeUiEvents(otpViewModel)
        smartUserConsent()
        isArabic()
        Timber.e(verificationCode)
        binding.btnContinueReg.setOnClickListener(this)
        binding.tvResend.setOnClickListener(this)
//        disabledBackButton()
//        loginWithConfirmOTPObserver()
//        if (from == "registerFragment") {
//            isCancelable = false
//        }

    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = PostOtpFragmentDialogBinding.inflate(inflater, container, false)

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
                    Constants.NavBundles.COMES_FROM_LOGIN -> {
                        otpViewModel.confirmLoginOTP(
                            verificationCode.toString(),
                            binding.otpView.text.toString()
                        )
                    }
                    COMES_FROM_POST_LOGIN -> {
                        otpViewModel.confirmLoginOTP(
                            verificationCode.toString(),
                            binding.otpView.text.toString()
                        )
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

    private fun disabledBackButton() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(false /* enabled by default */) {
                override fun handleOnBackPressed() {
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun loginWithConfirmOTPObserver() {
        otpViewModel.loginConfirmOTPStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it ->
                if (it) {
                    emailOrPhoneLogin?.let { data ->
                        if (data.startsWith("+")) {
                            loginViewModel.loginWithPhone(emailOrPhoneLogin, passwordlogin)
                        } else {
                            loginViewModel.loginWithEmail(emailOrPhoneLogin, passwordlogin)

                        }
                    }
                }
            }
        }

        loginViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it != null) {

                application.auth.isLoggedIn = true
                application.auth.isGuest = false
                activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)

            }
        }
//        loginViewModel.loginStatus.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let {
//                activity.killSessionAndStartNewActivity(ExploreActivity::class.java)
//            }
//        }
    }
}


