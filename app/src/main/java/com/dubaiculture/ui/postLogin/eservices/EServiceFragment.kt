package com.dubaiculture.ui.postLogin.eservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.FragmentEserviceBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.eservices.adapter.NocListAdapter
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EServiceFragment : BaseFragment<FragmentEserviceBinding>() {
    private lateinit var nocListAdapter: NocListAdapter
    private val eserviceViewModel: EServiceViewModel by viewModels()
    private var list: MutableList<String> = mutableListOf(
        "TextBox",
        "Dropdown",
        "TextBox",
        "Dropdown",
        "TextBox",
        "TextBox",
        "Dropdown",
        "TextBox",
        "Dropdown",
        "TextBox",
        "TextBox",
        "TextBox",
        "TextBox",
    )

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEserviceBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(eserviceViewModel)
        initRv()
        subscribeToObservable()
    }

    private fun subscribeToObservable(){
        eserviceViewModel.fieldValues.observe(viewLifecycleOwner){
            nocListAdapter.submitList(it)
        }
    }

    private fun initRv() {
        binding.fieldRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            nocListAdapter = NocListAdapter(object : FieldListener {
                override fun fetchInput(value: GetFieldValueItem) {
                    Toast.makeText(activity, value.selectedValue, Toast.LENGTH_SHORT).show()
                }

                override fun dropDownValue(value: GetFieldValueItem) {
                    Toast.makeText(activity, value.selectedValue, Toast.LENGTH_SHORT).show()
                }

                override fun dateValue(value: GetFieldValueItem) {
                    Toast.makeText(activity, value.selectedValue, Toast.LENGTH_SHORT).show()

                }

                override fun timeValue(value: GetFieldValueItem) {
                    Toast.makeText(activity, value.selectedValue, Toast.LENGTH_SHORT).show()
                }

            })

            adapter = nocListAdapter

        }
    }


}