package com.dubaiculture.ui.preLogin.registeration

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentRegisterBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.preLogin.registeration.viewmodel.RegistrationViewModel
import com.dubaiculture.utils.getColorFromAttr
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), View.OnClickListener {
    private val registrationViewModel: RegistrationViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        sharedElementEnterTransition =
            TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds)
                .setDuration(500)
        sharedElementReturnTransition =
            TransitionInflater.from(this.context).inflateTransition(R.transition.change_bounds)
                .setDuration(500)
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(registrationViewModel)

        binding.btnRegister.setOnClickListener(this)
        binding.tvLoginNow.setOnClickListener(this)
        binding.header.back.setOnClickListener(this)
        binding.tvTermCondition.setOnClickListener(this)

        binding.viewmodel = registrationViewModel
        lottieAnimationRTL(binding.animationView)
        backArrowRTL(binding.header.back)
        if (isArabic()) {
            val spannable =
                SpannableString(resources.getString(R.string.i_agree_to_the_terms_and_conditions))
            spannable.setSpan(
                ForegroundColorSpan(activity.getColorFromAttr(R.attr.colorSecondary)),
                0, 10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(UnderlineSpan(), 10, binding.tvTermCondition.length(), 36)
            binding.tvTermCondition.text = spannable
        } else {
            val spannable =
                SpannableString(resources.getString(R.string.i_agree_to_the_terms_and_conditions))
            spannable.setSpan(
                ForegroundColorSpan(activity.getColorFromAttr(R.attr.colorSecondary)),
                0, 14,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(UnderlineSpan(), 15, binding.tvTermCondition.length(), 27)
            binding.tvTermCondition.text = spannable
        }

        registrationViewModel.isTermAccepted.observe(viewLifecycleOwner) {
            if (it == false) {
                registrationViewModel.showErrorDialog(
                    message = resources.getString(R.string._agree_to_the_terms_and_conditions),
                    colorBg = R.color.red_600
                )
            }
        }
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
                registrationViewModel.showToast("Terms & Conditions")
            }

        }
    }

    private fun enterTransition(): Transition? {
        val bounds = ChangeBounds()
        bounds.duration = 2000
        return bounds
    }

    private fun returnTransition(): Transition? {
        val bounds = ChangeBounds()
        bounds.interpolator = DecelerateInterpolator()
        bounds.duration = 2000
        return bounds
    }
}