package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.app.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.app.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ProcedurePageFragment(val procedure: List<Procedure>?):  BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= ItemsServiceDetailInnerListingLayoutBinding.inflate(inflater,container,false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initRecycling()
    }


    fun initRecycling(){
        binding.innerRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
            adapter = paymentInnerAdapter
            procedure?.get(0)?.serviceProcedure?.forEach {
                val paymentsItem =
                    ServiceDetailListingItems<ItemsServiceDetailProcedureLayoutBinding, ServiceProcedure>(
                        eService = it,
                        resLayout = R.layout.items_service_detail_procedure_layout
                    )
                paymentInnerAdapter.add(paymentsItem)
            }
        }

    }

}