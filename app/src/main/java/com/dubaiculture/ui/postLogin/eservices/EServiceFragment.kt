package com.dubaiculture.ui.postLogin.eservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.FragmentEserviceBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createEditText
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createTextView
import com.dubaiculture.ui.postLogin.eservices.adapter.NocListAdapter
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceSharedViewModel
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EServiceFragment : BaseFragment<FragmentEserviceBinding>() {
    private lateinit var nocListAdapter: NocListAdapter
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
        initRv()
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
//            nocListAdapter.submitList(it)
        }
    }

    private fun initializeFields(fieldValues: List<GetFieldValueItem>) {
        fieldValues.forEach {

            when (FieldsTypeMode.fromName(it.fieldType).id) {
                FieldsType.LABEL.id -> {
                    binding.fieldContainer.addView(
                        createTextView(activity, it)
                    )
                }
                else -> {
                    when (FieldsType.fromName(it.valueType).id) {
                        FieldsType.DATE.id -> {
                            binding.fieldContainer.addView(
                                createTextView(activity, it)
                            )
                        }
                        FieldsType.IMAGE.id -> {
                            binding.fieldContainer.addView(
                                createTextView(activity, it)
                            )
                        }
                        FieldsType.FILE.id -> {
                            binding.fieldContainer.addView(
                                createTextView(activity, it)
                            )
                        }
                        FieldsType.DROP_DOWN.id -> {
                            binding.fieldContainer.addView(
                                createTextView(activity, it)
                            )
                        }
                        FieldsType.INPUT_TEXT.id -> {
                            binding.fieldContainer.addView(
                                createEditText(
                                    fieldValue = it,
                                    context = activity,
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
                        FieldsType.INPUT_NUMBER.id -> {
                            binding.fieldContainer.addView(
                                createEditText(
                                    fieldValue = it,
                                    context = activity,
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
                        FieldsType.INPUT_TEXT_MULTILINE.id -> {
                            binding.fieldContainer.addView(
                                createEditText(
                                    fieldValue = it,
                                    context = activity,
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

                    }
                }
            }
        }

    }


    private fun initRv() {
//        binding.fieldRv.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            nocListAdapter = NocListAdapter(object : FieldListener {
//                override fun fetchInput(value: GetFieldValueItem) {
////                    navigateByDirections(EServiceFragmentDirections.actionEServiceFragmentToInputPlateBottomSheet(
////                        value
////                    ))
//
//                    eserviceViewModel.updateFieldValue(value)
//                }
//
//                override fun dropDownValue(value: GetFieldValueItem) {
//                    eserviceViewModel.updateFieldValue(value.copy(selectedValue = value.selectedValue))
//                }
//
//                override fun dateValue(value: GetFieldValueItem) {
//                    eserviceViewModel.updateFieldValue(value.copy(selectedValue = value.selectedValue))
//                }
//
//                override fun timeValue(value: GetFieldValueItem) {
//                    eserviceViewModel.updateFieldValue(value.copy(selectedValue = value.selectedValue))
//                }
//
//            })
//
//            adapter = nocListAdapter
//
//        }
    }


}