package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
        if (!this::serviceProcedureListAdapter.isInitialized){
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
//            val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
            serviceProcedureListAdapter = ServiceProcedureListAdapter()
            adapter = serviceProcedureListAdapter
//            procedure?.get(0)?.serviceProcedure?.forEachIndexed { index, serviceProcedure ->
//                run {
//                    val paymentsItem =
//                        ServiceDetailListingItems<ItemsServiceDetailProcedureLayoutBinding, ServiceProcedure>(
//                            eService = serviceProcedure.copy(id = index),
//                            resLayout = R.layout.items_service_detail_procedure_layout
//                        )
//                    paymentInnerAdapter.add(paymentsItem)
//                }
//            }

        }

    }
}