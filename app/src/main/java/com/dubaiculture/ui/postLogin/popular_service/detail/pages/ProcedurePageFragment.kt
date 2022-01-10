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
import com.dubaiculture.utils.show

class ProcedurePageFragment(val procedure: List<Procedure>?) :
    BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    private lateinit var serviceProcedureListAdapter: ServiceProcedureListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemsServiceDetailInnerListingLayoutBinding.inflate(inflater, container, false)


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.detailListingHeader.text = activity.resources.getString(R.string.service_procedure)

        if (!this::serviceProcedureListAdapter.isInitialized) {
            initRecycling()
        }

    }


    fun initRecycling() {
        binding.innerRecyclerView.apply {
            if (procedure?.get(0)?.serviceProcedure!!.isEmpty()) {
                hide()
                binding.detailListingHeader.hide()
                binding.noDataPlaceHolder.show()

            }else {
                val linearLayoutManager = LinearLayoutManager(context)
                layoutManager = linearLayoutManager
                serviceProcedureListAdapter = ServiceProcedureListAdapter()
                adapter = serviceProcedureListAdapter
                procedure.get(0).serviceProcedure.let {
                    serviceProcedureListAdapter.submitList(it)
                }
            }


        }

    }
}