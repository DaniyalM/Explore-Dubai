package com.dubaiculture.ui.postLogin.eservices.dialog

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dubaiculture.databinding.FragmentInputPlateBottomSheetBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceBottomSheetViewModel
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputPlateBottomSheet : BaseBottomSheetFragment<FragmentInputPlateBottomSheetBinding>() {

    private val eServicesSharedViewModel: EServiceSharedViewModel by activityViewModels()
    private val inputPlateBottomSheetViewModel: EServiceBottomSheetViewModel by viewModels()
    private val inputPlateBottomSheetArgs: InputPlateBottomSheetArgs by navArgs()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentInputPlateBottomSheetBinding.inflate(inflater, container, false)


    override fun onStart() {
        super.onStart()
        isCancelable = false
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        subscribeUiEvents(inputPlateBottomSheetViewModel)
        subscribeToObservable()
        inputPlateBottomSheetArgs.field?.apply {
            binding.field = this
            inputPlateBottomSheetViewModel.field.set(selectedValue ?: "")
            if (english.toLowerCase().contains("number")) {
                binding.formInput.inputType = InputType.TYPE_CLASS_PHONE
            }

            if (english.toLowerCase().contains("email")){
                binding.formInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                inputPlateBottomSheetViewModel.isEmail.set(true)
            }else {
                inputPlateBottomSheetViewModel.isEmail.set(false)
            }
        }
        binding.inputPlateBottomSheetViewModel = inputPlateBottomSheetViewModel
        binding.img.setOnClickListener {
            dismiss()
        }

    }


    private fun subscribeToObservable() {
        inputPlateBottomSheetViewModel.fieldValue.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                eServicesSharedViewModel.updateField(
                    inputPlateBottomSheetArgs.field!!.copy(
                        selectedValue =
                        binding.formInput.text.toString().trim()
                    )
                )
                dismiss()
            }
        }
    }

}