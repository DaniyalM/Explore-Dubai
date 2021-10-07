package com.app.dubaiculture.ui.postLogin.login

import ae.sdg.libraryuaepass.UAEPassAccessCodeCallback
import ae.sdg.libraryuaepass.UAEPassController
import ae.sdg.libraryuaepass.UAEPassProfileCallback
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import ae.sdg.libraryuaepass.business.profile.model.ProfileModel
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestByAccessTokenModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.login.local.UaeLoginRequest
import com.app.dubaiculture.databinding.FragmentPostLoginBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.PostLoginActivity
import com.app.dubaiculture.ui.postLogin.login.viewmodel.PostLoginViewModel
import com.app.dubaiculture.ui.preLogin.bus.UAEPassService
import com.app.dubaiculture.ui.preLogin.splash.viewmodels.SplashViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.MORE_FRAGMENT
import com.app.dubaiculture.utils.UAEPassRequestModelsUtils
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostLoginFragment : BaseBottomSheetFragment<FragmentPostLoginBinding>(),
    View.OnClickListener {
    private val postLoginViewModel: PostLoginViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()
    private var postCreatePassFragment: PostCreatePassFragment? = null
    private var postRegisterFragment: PostRegisterFragment? = null
    private var postForgotFragment: PostForgotFragment? = null
    private var from: String? = null

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
        binding.editMobile.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {

            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                dismiss()
            }
            R.id.img_uae_pass -> {
//                getCode()

//                bus.post(UAEPassService.UaeClick(true))
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
        postLoginViewModel.loginStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                splashViewModel.getUserIfExists()
            }
        }
        splashViewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                application.auth.user = it
            }
            dismiss()
            if (!from.isNullOrEmpty()) {
                activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
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




}