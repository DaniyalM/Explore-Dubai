package com.app.dubaiculture.ui.preLogin.password.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPasswordUpdatedBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_password_updated.view.*

class PasswordUpdatedFragment : BaseBottomSheetFragment<FragmentPasswordUpdatedBinding>(), View.OnClickListener {

    private var dismissWithAnimation = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnContinue.setOnClickListener(this)
        dismissWithAnimation = arguments?.getBoolean(ARG_DISMISS_WITH_ANIMATION) ?: false
        (requireDialog() as BottomSheetDialog).dismissWithAnimation = dismissWithAnimation

    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentPasswordUpdatedBinding.inflate(inflater,container,false)

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_continue->{

            }
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        private const val ARG_DISMISS_WITH_ANIMATION = "dismiss_with_animation"
        fun newInstance(dismissWithAnimation: Boolean): PasswordUpdatedFragment {
            val resetPassBottomSheet = PasswordUpdatedFragment()
            resetPassBottomSheet.arguments = bundleOf(ARG_DISMISS_WITH_ANIMATION to dismissWithAnimation)
            return resetPassBottomSheet
        }
    }
}