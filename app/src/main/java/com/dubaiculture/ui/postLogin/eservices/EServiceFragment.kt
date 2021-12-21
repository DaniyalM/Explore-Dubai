package com.dubaiculture.ui.postLogin.eservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.FragmentEserviceBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createDropDown
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createEditText
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createTextView
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceSharedViewModel
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EServiceFragment : BaseFragment<FragmentEserviceBinding>() {
    private val eServicesSharedViewModel: EServiceSharedViewModel by activityViewModels()
    private val eserviceViewModel: EServiceViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEserviceBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(eserviceViewModel)
        binding.include.back.setOnClickListener {
            back()
        }
        subscribeToObservable()
    }

    private fun subscribeToObservable() {
        eServicesSharedViewModel.updateField.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                eserviceViewModel.updateFieldValue(it)
            }
        }

        eserviceViewModel.fieldValue.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                eserviceViewModel.updateList(it)
            }
        }
        eserviceViewModel.fieldValues.observe(viewLifecycleOwner) {
            initializeFields(it)
        }
    }

    private fun initializeFields(fieldValues: List<GetFieldValueItem>) {
        fieldValues.forEach {

            when (FieldType.fromName(it.fieldType).id) {
                FieldType.LABEL.id -> {
                    binding.fieldContainer.addView(
                        createTextView(activity, it)
                    )
                }
                else -> {
                    when (ValueType.fromName(it.valueType).id) {
                        ValueType.INPUT_TEXT.id -> {
                            binding.fieldContainer.addView(
                                createEditText(
                                    fieldValue = it,
                                    context = requireContext(),
                                    fieldListener = object : FieldListener {
                                        override fun fetchInput(value: GetFieldValueItem) {

                                        }

                                        override fun dropDownValue(value: GetFieldValueItem) {
                                        }

                                        override fun dateValue(value: GetFieldValueItem) {
                                        }

                                        override fun timeValue(value: GetFieldValueItem) {
                                        }
                                    })
                            )
                        }
                        ValueType.INPUT_NUMBER.id -> {
                            binding.fieldContainer.addView(
                                createEditText(
                                    fieldValue = it,
                                    context = requireContext(),
                                    fieldListener = object : FieldListener {
                                        override fun fetchInput(value: GetFieldValueItem) {

                                        }

                                        override fun dropDownValue(value: GetFieldValueItem) {
                                        }

                                        override fun dateValue(value: GetFieldValueItem) {
                                        }

                                        override fun timeValue(value: GetFieldValueItem) {
                                        }
                                    })
                            )
                        }
                        ValueType.INPUT_TEXT_MULTILINE.id -> {
                            binding.fieldContainer.addView(
                                createEditText(
                                    fieldValue = it,
                                    context = requireContext(),
                                    fieldListener = object : FieldListener {
                                        override fun fetchInput(value: GetFieldValueItem) {

                                        }

                                        override fun dropDownValue(value: GetFieldValueItem) {
                                        }

                                        override fun dateValue(value: GetFieldValueItem) {
                                        }

                                        override fun timeValue(value: GetFieldValueItem) {
                                        }
                                    })
                            )
                        }
                        ValueType.DATE.id -> {
                            binding.fieldContainer.addView(
                                createTextView(requireContext(), it)
                            )
                        }
                        ValueType.IMAGE.id -> {
                            binding.fieldContainer.addView(
                                createTextView(requireContext(), it)
                            )
                        }
                        ValueType.FILE.id -> {
                            binding.fieldContainer.addView(
                                createTextView(requireContext(), it)
                            )
                        }
                        ValueType.DROP_DOWN.id -> {
                            binding.fieldContainer.addView(
                                createDropDown(requireContext(), it)
                            )
                        }
                    }

                }
            }
        }

    }

}