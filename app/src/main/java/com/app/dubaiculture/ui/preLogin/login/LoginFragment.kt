package com.app.dubaiculture.ui.preLogin.login

import ae.sdg.libraryuaepass.UAEPassAccessCodeCallback
import ae.sdg.libraryuaepass.UAEPassAccessTokenCallback
import ae.sdg.libraryuaepass.UAEPassController.getAccessCode
import ae.sdg.libraryuaepass.UAEPassController.getAccessToken
import ae.sdg.libraryuaepass.UAEPassController.getUserProfile
import ae.sdg.libraryuaepass.UAEPassController.getUserProfileByAccessToken
import ae.sdg.libraryuaepass.UAEPassProfileCallback
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import ae.sdg.libraryuaepass.business.profile.model.ProfileModel
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestByAccessTokenModel
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestModel
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.app.dubaiculture.databinding.FragmentLoginBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.Constants.Error.UAE_PASS_ERROR
import com.app.dubaiculture.utils.SMSReceiver
import com.app.dubaiculture.utils.UAEPassRequestModelsUtils
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import com.estimote.coresdk.common.requirements.SystemRequirementsHelper
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


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
//        Timber.e("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());

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

    }


    private fun subscribeToObservables() {
        loginViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                application.auth.apply {
                    user = it
                    isGuest = false
                    isLoggedIn = true
                    loginViewModel.getGuestUserIfExists()
//                    if (it.hasPassword){
//
//                    }else {
//                        if(it.verificationToken.isNotEmpty()){
//                            navigateByAction(R.id.action_loginFragment_to_createPassFragment,Bundle().apply {
//                                putString("verificationCode",it.verificationToken)
//                            })
//                        }
//
//                    }

                }



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
                login()
//                getCode()
//                navigate(R.id.action_loginFragment_to_bottomSheet)
//                getCode()
//                getProfile()
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

    private fun getCode() {
//        val uaePassRequestModels = UAEPassRequestModels()

        val requestModel: UAEPassAccessTokenRequestModel =
            UAEPassRequestModelsUtils.getAuthenticationRequestModel(context)!!
        getAccessCode(activity, requestModel, object : UAEPassAccessCodeCallback {
            override fun getAccessCode(code: String?, error: String?) {
                if (error != null) {
                    showAlert(error)
                } else {
//                        showToast("Access Token Received")
                    code?.let {
                        getProfileAccessToken(it)

                    }
                }
            }
        })
    }

    /**
     * Login with UAE Pass and get the access Code.
     */
//    private fun getCode() {
//        val uaePassRequestModels = UAEPassRequestModels()
//        val requestModel: UAEPassAccessTokenRequestModel? =
//            uaePassRequestModels.getAuthenticationRequestModel(
//                activity
//            )
//        requestModel?.let {
//            getAccessCode(activity, it, object : UAEPassAccessCodeCallback {
//                override fun getAccessCode(code: String?, error: String?) {
//                    if (error != null) {
//                        showAlert(error)
//                    } else {
////                        showToast("Access Token Received")
//                        code?.let {
//                            getProfileAccessToken(it)
//
//                        }
//                    }
//                }
//            })
//        }
//    }

    private fun login() {
//        val uaePassRequestModels = UAEPassRequestModels()
        showLoader(true)
        val requestModel: UAEPassAccessTokenRequestModel =
            UAEPassRequestModelsUtils.getAuthenticationRequestModel(
                activity
            )!!
        getAccessToken(activity, requestModel, object : UAEPassAccessTokenCallback {
            override fun getToken(accessToken: String?, state: String, error: String?) {
                if (error != null) {
                    showAlert(UAE_PASS_ERROR)
                    showLoader(false)
//                    showToast("Error while getting access token")
                } else {
                    accessToken?.let {
                        Timber.e("Token : $it")
                        loginViewModel.loginWithUae(
                            UAELoginRequest(
                                token = it,
                                culture = getCurrentLanguage().language
                            )
                        )
                        clearData()
//                        getProfileAccessToken(it)
                    }

                }
            }
        })
    }


    private fun getProfile() {
        val requestModel: UAEPassProfileRequestModel =
            UAEPassRequestModelsUtils.getProfileRequestModel(application.applicationContext)
        getUserProfile(
            application.applicationContext,
            requestModel,
            object : UAEPassProfileCallback {
                override fun getProfile(
                    profileModel: ProfileModel?,
                    state: String,
                    error: String?
                ) {
                    if (error != null) {
                        Toast.makeText(
                            activity,
                            "Error while getting access token",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        val name =
                            profileModel!!.firstnameEN + profileModel.homeAddressEmirateCode + " " + profileModel.lastnameEN
                        Toast.makeText(activity, "Welcome $name", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun clearData() {
        CookieManager.getInstance().removeAllCookies { }
        CookieManager.getInstance().flush()
    }

    private fun getProfileAccessToken(at: String) {
//        val uaePassRequestModels = UAEPassRequestModels()
        val rm: UAEPassProfileRequestByAccessTokenModel =
            UAEPassRequestModelsUtils.getUAEPassHavingAccessToken(at)
        getUserProfileByAccessToken(
            rm, object : UAEPassProfileCallback {
                override fun getProfile(
                    profileModel: ProfileModel?,
                    state: String,
                    error: String?
                ) {
                    if (error != null) {
                        showLoader(false)

                        showAlert(error)
                    } else {
                        showLoader(false)
                        profileModel?.let {
//                            UAEPassController.resume("uaePassDemo")

                            if (it.idn != null && it.idn!!.isNotEmpty()) {

                                clearData()
                            } else {
                                showAlert(activity.resources.getString(R.string.sop1))
                            }
                        }
                    }
                }
            })
    }


}


//    idn = it.idn!!,
//    idType = it.idType?:"",
//    email = it.email?:"",
//    mobile = it.mobile?:"",
//    firstNameEn = it.firstnameEN?:"",
//    firstNameAr = it.firstnameAR?:"",
//    lastNameAr = it.lastnameAR?:"",
//    lastNameEn = it.lastnameEN?:"",
//    fullNameAr = it.fullnameAR?:"",
//    fullNameEn = it.fullnameEN?:"",
//    acr = it.acr?:"",
//    nationalityAr = it.nationalityAR?:"",
//    nationalityEn = it.nationalityEN?:"",
//    gender = it.gender?:"",
//    spuuid = it.spuuid?:"",
//    sub = it.sub?:"",
//    titleAr = it.titleAR?:"",
//    titleEn = it.titleEN?:"",
//    user_type = it.userType?:""