package com.dubaiculture.ui.postLogin.eservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.databinding.FragmentEserviceBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.eservices.adapter.NocListAdapter
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EServiceFragment : BaseFragment<FragmentEserviceBinding>() {
    private lateinit var nocListAdapter: NocListAdapter

    private var list:MutableList<String> = mutableListOf(
        "TextBox",
        "TextBox",
        "TextBox",
        "TextBox",
        "TextBox",
        "TextBox",
        "TextBox",
        "TextBox",
        "TextBox",
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
        initRv()
    }

    private fun initRv() {
        binding.fieldRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            nocListAdapter = NocListAdapter(object : FieldListener {
                override fun fetchInput(value: String) {
                    Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
                }
            })

            adapter = nocListAdapter
            nocListAdapter.submitList(list)
        }
    }


}