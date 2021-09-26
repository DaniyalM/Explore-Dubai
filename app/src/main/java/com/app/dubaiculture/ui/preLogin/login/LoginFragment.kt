package com.app.dubaiculture.ui.preLogin.login

import ae.sdg.libraryuaepass.UAEPassAccessCodeCallback
import ae.sdg.libraryuaepass.UAEPassAccessTokenCallback
import ae.sdg.libraryuaepass.UAEPassController
import ae.sdg.libraryuaepass.UAEPassController.getAccessCode
import ae.sdg.libraryuaepass.UAEPassController.getAccessToken
import ae.sdg.libraryuaepass.UAEPassController.getUserProfile
import ae.sdg.libraryuaepass.UAEPassProfileCallback
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import ae.sdg.libraryuaepass.business.profile.model.ProfileModel
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestModel
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLoginBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.SMSReceiver
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import com.app.dubaiculture.utils.uaePassUtils.UAEPassRequestModels
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import com.estimote.coresdk.common.requirements.SystemRequirementsHelper
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.iid.FirebaseInstanceId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {

    private val loginViewModel: LoginViewModel by viewModels()
    private var intentFilter: IntentFilter? = null
    private var smsReceiver: SMSReceiver? = null
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
        initSmsListener()
        initBroadCast()
        applicationExitDialog()
        binding.fragment = this
        binding.forgotPass.setOnClickListener(this)
        binding.imgUaePass.setOnClickListener(this)
        Log.e("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());

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
            if (SystemRequirementsHelper.isLocationServiceForBluetoothLeEnabled(requireContext()) && SystemRequirementsHelper.isBluetoothEnabled(
                            requireContext()
                    )
            ) {

                application.auth.apply {
                    isLoggedIn = true
                    isGuest = true
                }
//            activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
                activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
            } else if (!SystemRequirementsHelper.isBluetoothEnabled(requireContext())) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 1)
            } else {
                SystemRequirementsChecker.checkWithDefaultDialogs(requireActivity())
            }
        }



        subscribeToObservables()

        if (isArabic()) {
            binding.imgUaePass.setImageResource(R.drawable.uae_pass_new)
        } else {
            binding.imgUaePass.setImageResource(R.drawable.uae_pass)
        }
        lifecycleScope.launch {
//            Timber.e("Token: ${getFcmToken()}")
        }
    }


    private fun subscribeToObservables() {
        loginViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                application.auth.user = it
                application.auth.isGuest = false
                application.auth.isLoggedIn = true
                loginViewModel.getGuestUserIfExists()

            }
        }

        loginViewModel.userGuestLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                loginViewModel.removeGuestUser(it)
            }
            activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)

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
//                login()
//                getCode()
//                navigate(R.id.action_loginFragment_to_bottomSheet)
//                getCode()
                getProfile()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()
        activity.registerReceiver(smsReceiver, intentFilter)
    }


    override fun onPause() {
        super.onPause()
        loginViewModel.isPassword.value = true
        loginViewModel.isPhone.value = true
        loginViewModel.isPhoneEdit.value = true
        loginViewModel.isEmailEdit.value = true
        loginViewModel.isEmail.value = true
        activity.unregisterReceiver(smsReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        smsReceiver = null
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

    private fun initBroadCast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsReceiver = SMSReceiver()
        smsReceiver?.setOTPListener(object : SMSReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                loginViewModel.showToast("OTP Received: $otp")
            }
        })
    }

    private fun initSmsListener() {
        val client = SmsRetriever.getClient(activity)
        client.startSmsRetriever()
    }

    /**
     * Login with UAE Pass and get the access Code.
     */
    private fun getCode() {
        val uaePassRequestModels = UAEPassRequestModels()
        val requestModel: UAEPassAccessTokenRequestModel? = uaePassRequestModels.getAuthenticationRequestModel(
                activity
        )
        requestModel?.let {
            getAccessCode(activity, it, object : UAEPassAccessCodeCallback {
                override fun getAccessCode(code: String?, error: String?) {
                    if (error != null) {
                        Toast.makeText(
                                activity,
                                "Error while getting access token",
                                Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(activity, "Access Code Received", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            })
        }
    }

    private fun login() {
        val uaePassRequestModels = UAEPassRequestModels()
        val requestModel: UAEPassAccessTokenRequestModel? = uaePassRequestModels.getAuthenticationRequestModel(
                activity
        )
        getAccessToken(activity, requestModel!!, object : UAEPassAccessTokenCallback {
            override fun getToken(accessToken: String?, state: String, error: String?) {
                if (error != null) {
                    Toast.makeText(
                            activity,
                            "Error while getting access token",
                            Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(activity, "Access Token Received", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })
    }




    private fun getProfile() {
        val uaePassRequestModels = UAEPassRequestModels()
        val requestModel: UAEPassProfileRequestModel = uaePassRequestModels.getProfileRequestModel(activity)!!
        getUserProfile(activity, requestModel, object : UAEPassProfileCallback {
            override fun getProfile(profileModel: ProfileModel?, state: String, error: String?) {
                if (error != null) {
                    Toast.makeText(activity, "Error while getting access token", Toast.LENGTH_SHORT).show()
                } else {
                    val name = profileModel!!.firstnameEN +profileModel!!.homeAddressEmirateCode+ " " + profileModel.lastnameEN
                    Toast.makeText(activity, "Welcome $name", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}