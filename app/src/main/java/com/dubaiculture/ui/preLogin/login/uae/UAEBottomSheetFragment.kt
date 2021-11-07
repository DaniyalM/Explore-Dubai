package com.dubaiculture.ui.preLogin.login.uae

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dubaiculture.databinding.FragmentUaePassLinkingBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.preLogin.login.uae.viewmodels.UaePassSharedViewModel
import com.dubaiculture.ui.preLogin.login.uae.viewmodels.UaePassViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UAEBottomSheetFragment : BaseBottomSheetFragment<FragmentUaePassLinkingBinding>() {
    private val uaePassSharedViewModel: UaePassSharedViewModel by activityViewModels()
    private val uaePassViewModel: UaePassViewModel by viewModels()

    interface OnUserCheckedChangeListener {
        fun onUserCheckChange(view: CompoundButton, isChecked: Boolean)
    }

    fun onCrossClick() {
        dismiss()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUaePassLinkingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(uaePassViewModel)
        dialog?.setCanceledOnTouchOutside(false)
        subscribeToObservable()

    }


    private fun subscribeToObservable() {
        uaePassViewModel.updateLinkingRequest.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                uaePassSharedViewModel.registerWithUaePass(it)
            }
        }

        uaePassSharedViewModel.sheetOpen.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it)
                    dismiss()
            }
        }
    }
}