package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.app.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceProcedureListAdapter

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
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            serviceProcedureListAdapter = ServiceProcedureListAdapter()
            adapter = serviceProcedureListAdapter

        }

    }
}