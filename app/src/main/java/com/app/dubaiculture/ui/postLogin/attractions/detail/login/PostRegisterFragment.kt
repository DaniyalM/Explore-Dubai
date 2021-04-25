package com.app.dubaiculture.ui.postLogin.attractions.detail.login

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPostRegisterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.login.viewmodel.PostRegisterViewModel
import com.app.dubaiculture.ui.preLogin.registeration.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostRegisterFragment : BaseFragment<FragmentPostRegisterBinding>(),View.OnClickListener {
    private val postRegisterViewModel: PostRegisterViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentPostRegisterBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(postRegisterViewModel)
        binding.btnRegister.setOnClickListener(this)
        binding.tvLoginNow.setOnClickListener(this)
        binding.header.back.setOnClickListener(this)
        binding.tvTermCondition.setOnClickListener(this)
        binding.viewmodel = postRegisterViewModel
        lottieAnimationRTL(binding!!.animationView)
        backArrowRTL(binding.header.back)
        spannable()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                navigate(R.id.action_registerFragment2_to_bottomSheet)
            }
            R.id.tv_login_now -> {
                back()
            }
            R.id.back -> {
                back()
            }
            R.id.tv_term_condition -> {
                postRegisterViewModel.showToast("Terms & Conditions")
            }
        }
    }
    private fun spannable(){
        if(isArabic()){
            val spannable = SpannableString(resources.getString(R.string.i_agree_to_the_terms_and_conditions))
            spannable.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.black_200)),
                0, 10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(UnderlineSpan(), 10, binding.tvTermCondition.length(), 36)
            binding.tvTermCondition.text = spannable
        }else{
            val spannable = SpannableString(resources.getString(R.string.i_agree_to_the_terms_and_conditions))
            spannable.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.black_200)),
                0, 14,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(UnderlineSpan(), 15, binding!!.tvTermCondition.length(), 27)
            binding!!.tvTermCondition.text = spannable
        }

        postRegisterViewModel.isTermAccepted.observe(viewLifecycleOwner){
            if(it==false){
                postRegisterViewModel.showErrorDialog(message = resources.getString(R.string._agree_to_the_terms_and_conditions),colorBg = R.color.red_600)

            }
        }
    }
}