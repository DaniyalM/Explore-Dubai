package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.FAQ
import com.app.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class FaqsFragment(val fAQs: List<FAQ>) : BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemsServiceDetailInnerListingLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailListingHeader.text = activity.resources.getString(R.string.faqs)

        initRecycling()
    }

    fun initRecycling() {
        binding.innerRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(activity)
            layoutManager = linearLayoutManager
            val faqInnerAdapter = GroupAdapter<GroupieViewHolder>()
            adapter = faqInnerAdapter
            fAQs.forEach {
                val faqItem =
                    ServiceDetailListingItems<ItemsServiceDetailInnerListingLayoutBinding, FAQ>(
                        eService = it,
                        resLayout = R.layout.items_service_detail_inner_listing_layout
                    )

                faqInnerAdapter.add(faqItem)

            }
        }

    }
}