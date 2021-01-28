package com.app.dubaiculture.ui.preLogin.registeration.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.app.dubaiculture.databinding.FragmentOTPBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class OTPFragment : BaseBottomSheetFragment<FragmentOTPBinding>() {
    private var dismissWithAnimation = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dismissWithAnimation =
            arguments?.getBoolean(ARG_DISMISS_WITH_ANIMATION) ?: false
        (requireDialog() as BottomSheetDialog).dismissWithAnimation = dismissWithAnimation
        binding.btnContinue.setOnClickListener {view->
//            Navigation.findNavController(view).navigate(R.id.)
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentOTPBinding.inflate(inflater,container,false)
    companion object {
        const val TAG = "ModalBottomSheet"
        private const val ARG_DISMISS_WITH_ANIMATION = "dismiss_with_animation"
        fun newInstance(dismissWithAnimation: Boolean): OTPFragment {
            val resetPassBottomSheet = OTPFragment()
            resetPassBottomSheet.arguments = bundleOf(ARG_DISMISS_WITH_ANIMATION to dismissWithAnimation)
            return resetPassBottomSheet
        }
    }

}