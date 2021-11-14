package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServiceProcedureListAdapter
import com.dubaiculture.utils.hide

class ProcedurePageFragment(val procedure: List<Procedure>?) :
    BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    private lateinit var serviceProcedureListAdapter: ServiceProcedureListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemsServiceDetailInnerListingLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailListingHeader.text = activity.resources.getString(R.string.service_procedure)

        if (!this::serviceProcedureListAdapter.isInitialized) {
            initRecycling()
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        procedure?.get(0)?.serviceProcedure?.let {
            serviceProcedureListAdapter.submitList(it)
        }

    }


    fun initRecycling() {
        binding.innerRecyclerView.apply {
            if (procedure?.get(0)?.serviceProcedure!!.isEmpty()){
                hide()
            }
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            serviceProcedureListAdapter = ServiceProcedureListAdapter()
            adapter = serviceProcedureListAdapter

        }

    }
}