package com.app.dubaiculture.ui.preLogin.registeration.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentOTPBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.preLogin.registeration.otp.viewmodel.OTPViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OTPFragment : BaseBottomSheetFragment<FragmentOTPBinding>(), View.OnClickListener {
    private var dismissWithAnimation = false
    private val otpViewModel: OTPViewModel by viewModels()
    private var verificationCode: String? = null
    private var from: String? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = otpViewModel
        subscribeUiEvents(otpViewModel)
        arguments?.let {
            verificationCode = it.getString("verificationCode")
            from = it.getString("screen_name")
        }
//        from = arguments?.getString("key")
//        from = arguments?.getString("from")
        Timber.e(verificationCode)



        dismissWithAnimation =
            arguments?.getBoolean(ARG_DISMISS_WITH_ANIMATION) ?: false
        (requireDialog() as BottomSheetDialog).dismissWithAnimation = dismissWithAnimation
        binding.btnContinueReg.setOnClickListener(this)
        binding.tvResend.setOnClickListener(this)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOTPBinding.inflate(inflater, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
        private const val ARG_DISMISS_WITH_ANIMATION = "dismiss_with_animation"
        fun newInstance(dismissWithAnimation: Boolean): OTPFragment {
            val resetPassBottomSheet = OTPFragment()
            resetPassBottomSheet.arguments =
                bundleOf(ARG_DISMISS_WITH_ANIMATION to dismissWithAnimation)
            return resetPassBottomSheet
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_continue_reg -> {
                if (from == "forgotfragment") {
                    navigate(R.id.action_bottomSheet_to_createPassFragment)
                    otpViewModel.validateOTP(verificationCode!!,binding.otpView.text.toString().trim())
                } else {
                    verificationCode?.let {
                        otpViewModel.confirmOTP(it, binding.otpView.text.toString())
                    }
                }
            }
            R.id.tvResend -> {
                verificationCode?.let { otpViewModel.resendOTP(it) }
            }
        }
    }

}