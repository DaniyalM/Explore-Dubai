package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.RequiredDocument
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.app.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.app.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class RequirementPageFragment(val requiredDocument: List<RequiredDocument>?) : BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= ItemsServiceDetailInnerListingLayoutBinding.inflate(inflater,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailListingHeader.text = activity.resources.getString(R.string.required_documents)

        initRecycling()

    }
    fun initRecycling(){
        binding.innerRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
            adapter = paymentInnerAdapter
            requiredDocument?.get(0)?.let {
                val paymentsItem =
                    ServiceDetailListingItems<ItemsServiceDetailInnerListingLayoutBinding, RequiredDocument>(
                        eService = it,
                        resLayout = R.layout.items_service_detail_inner_listing_layout
                    )
                paymentInnerAdapter.add(paymentsItem)
            }
        }

    }
}