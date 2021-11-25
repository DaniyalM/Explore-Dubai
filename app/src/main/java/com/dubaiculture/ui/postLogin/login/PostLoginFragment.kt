package com.dubaiculture.ui.postLogin.login

import ae.sdg.libraryuaepass.UAEPassAccessTokenCallback
import ae.sdg.libraryuaepass.UAEPassController
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import androidx.datastore.preferences.preferencesKey
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dubaiculture.R
import com.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.dubaiculture.databinding.FragmentPostLoginBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.PostLoginActivity
import com.dubaiculture.ui.postLogin.login.viewmodel.PostLoginViewModel
import com.dubaiculture.ui.preLogin.login.LoginFragmentDirections
import com.dubaiculture.ui.preLogin.login.uae.viewmodels.UaePassSharedViewModel
import com.dubaiculture.ui.preLogin.splash.viewmodels.SplashViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.Error.UAE_PASS_ERROR
import com.dubaiculture.utils.Constants.NavBundles.MORE_FRAGMENT
import com.dubaiculture.utils.UAEPassRequestModelsUtils
import com.dubaiculture.utils.dataStore.DataStoreManager
import com.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class PostLoginFragment : BaseBottomSheetFragment<FragmentPostLoginBinding>(),
    View.OnClickListener {
    private val postLoginViewModel: PostLoginViewModel by viewModels()
    private val uaePassSharedViewModel: UaePassSharedViewModel by activityViewModels()

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val splashViewModel: SplashViewModel by viewModels()
    private var postCreatePassFragment: PostCreatePassFragment? = null
    private var postRegisterFragment: PostRegisterFragment? = null
    private var postForgotFragment: PostForgotFragment? = null
    private var from: String? = null
    private var token: String? = null

    init {
        postCreatePassFragment = PostCreatePassFragment()
        postRegisterFragment = PostRegisterFragment()
        postForgotFragment = PostForgotFragment()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentPostLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            from = it.getString(MORE_FRAGMENT)
        }

        binding.viewmodel = postLoginViewModel
        binding.fragment = this
        subscribeUiEvents(postLoginViewModel)
        binding.btnLogin.setOnClickListener(this)
        binding.imgUaePass.setOnClickListener(this)
        binding.tvRegisterNow.setOnClickListener(this)
        binding.tvForgotPass.setOnClickListener(this)
        callingObserver()
        uaePassRTL()
        binding.editMobile.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {

            }

        }

        lifecycleScope.launch {
            checkRememberMe()
        }

    }

    private suspend fun checkRememberMe() {

//        binding.checkBoxRemember.isChecked =
//            !dataStoreManager.getString(preferencesKey(Constants.DataStore.USERNAME)).equals("")
        binding.checkBoxRemember.isChecked = !(dataStoreManager.getString(preferencesKey(Constants.DataStore.USERNAME)) == null|| dataStoreManager.getString(preferencesKey(Constants.DataStore.USERNAME)).equals(""))

        postLoginViewModel.phone.set(dataStoreManager.getString(preferencesKey(Constants.DataStore.USERNAME)))
        postLoginViewModel.password.set(dataStoreManager.getString(preferencesKey(Constants.DataStore.PASSWORD)))

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                dismiss()
            }
            R.id.img_uae_pass -> {
//                getCode()

//                bus.post(UAEPassService.UaeClick(true))
                login()
//                dismiss()


//                getProfile()
//                navigate(R.id.action_postLoginFragment_to_postCreatePassFragment)
            }
            R.id.tv_register_now -> {
//                openFragment(postRegisterFragment!!,"PostRegister")
                navigate(R.id.action_postLoginFragment_to_postRegisterFragment)
            }
            R.id.tv_forgot_pass -> {
                navigate(R.id.action_postLoginFragment_to_postForgotFragment)
            }
        }
    }

    private fun callingObserver() {
        uaePassSharedViewModel.isLinkingRequest.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (!it.isAccountCreate!!) {
                    postLoginViewModel.loginWithUae(
                        it.copy(
                            token = token,
                            culture = getCurrentLanguage().language
                        ), true
                    )
                } else {
                    postLoginViewModel.loginWithUaeCreate(
                        it.copy(
                            token = token,
                            culture = getCurrentLanguage().language
                        )
                    )
                }

            }
        }
        postLoginViewModel.isSheetOpen.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it)
                    navigateByDirections(
                        PostLoginFragmentDirections.actionPostLoginFragmentToUAEBottomSheetFragment(
                            token!!
                        )
                    )
            }
        }

        postLoginViewModel.loginStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                splashViewModel.getUserIfExists()
            }
        }
        splashViewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                if (binding.checkBoxRemember.isChecked) {
                    lifecycleScope.launch {
                        dataStoreManager.setData(
                            preferencesKey(Constants.DataStore.USERNAME),
                            postLoginViewModel.phone.get()
                        )
                        dataStoreManager.setData(
                            preferencesKey(Constants.DataStore.PASSWORD),
                            postLoginViewModel.password.get()
                        )
                    }
                } else {

                    lifecycleScope.launch {
                        dataStoreManager.setData(
                            preferencesKey(Constants.DataStore.USERNAME),
                            ""
                        )
                        dataStoreManager.setData(
                            preferencesKey(Constants.DataStore.PASSWORD),
                            ""
                        )
                    }

                }
                application.auth.apply {
                    postLoginViewModel.updateSheet(false)
                    user = it
                    dismiss()
                    if (!from.isNullOrEmpty()) {
                        activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
                    }
//                    if (it.hasPassword){
//                        isGuest = false
//                        isLoggedIn = true
//                        dismiss()
//                        showToast("Welcome !")
//                        if (!from.isNullOrEmpty()) {
//                            activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
//                        }
//                    }else {
//                        if(it.verificationToken.isNotEmpty()){
//                            navigateByAction(R.id.action_postLoginFragment_to_postCreatePassFragment,Bundle().apply {
//                                putString("verificationCode",it.verificationToken)
//                                putBoolean("isHome",true)
//                            })
//                        }
//
//                    }

                }


            }

        }
    }

    private fun uaePassRTL() {
        if (isArabic()) {
            binding.imgUaePass.setImageResource(R.drawable.uae_pass_new)
        } else {
            binding.imgUaePass.setImageResource(R.drawable.uae_pass)
        }
    }

    override fun onPause() {
        super.onPause()
        postLoginViewModel.isPassword.value = true
        postLoginViewModel.isPhone.value = true
        postLoginViewModel.isPhoneEdit.value = true
        postLoginViewModel.isEmailEdit.value = true
        postLoginViewModel.isEmail.value = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postForgotFragment = null
        postRegisterFragment = null
        postCreatePassFragment = null

    }


    override fun onDestroy() {
        super.onDestroy()
        showLoader(false)
    }


    private fun login() {
//        val uaePassRequestModels = UAEPassRequestModels()
        showLoader(true)
        val requestModel: UAEPassAccessTokenRequestModel =
            UAEPassRequestModelsUtils.getAuthenticationRequestModelPostLogin(
                activity
            )!!
        UAEPassController.getAccessToken(
            activity,
            requestModel,
            object : UAEPassAccessTokenCallback {
                override fun getToken(accessToken: String?, state: String, error: String?) {
                    if (error != null) {
                        showAlert(activity.resources.getString(R.string.user_canceled_the_login))
                        showLoader(false)
                    } else {
                        accessToken?.let {
                            token=it
                            postLoginViewModel.loginWithUae(
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

    private fun clearData() {
        CookieManager.getInstance().removeAllCookies { }
        CookieManager.getInstance().flush()
    }


}