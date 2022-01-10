package com.dubaiculture.ui.preLogin.login.uae

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dubaiculture.databinding.FragmentUaePassLinkingBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.preLogin.login.uae.viewmodels.UaePassSharedViewModel
import com.dubaiculture.ui.preLogin.login.uae.viewmodels.UaePassViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UAEBottomSheetFragment : BaseBottomSheetFragment<FragmentUaePassLinkingBinding>() {
    private val uaePassSharedViewModel: UaePassSharedViewModel by activityViewModels()
    private val uaeBottomSheetFragmentArgs: UAEBottomSheetFragmentArgs by navArgs()
    private val uaePassViewModel: UaePassViewModel by viewModels()


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
        binding.viewmodel = uaePassViewModel
        binding.fragment=this
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        subscribeToObservable()


    }


    private fun subscribeToObservable() {
        uaePassViewModel.updateLinkingRequest.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {

//                    uaePassSharedViewModel.registerWithUaePass(
//                        it.copy(
//                            token = uaeBottomSheetFragmentArgs.token,
//                            culture = getCurrentLanguage().language
//                        )
//                    )
                uaePassSharedViewModel.dontCreateAccount()


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