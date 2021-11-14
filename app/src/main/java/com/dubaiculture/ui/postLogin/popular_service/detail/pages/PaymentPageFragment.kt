package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.Payment
import com.dubaiculture.data.repository.popular_service.local.models.PaymentX
import com.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.dubaiculture.databinding.ItemsServiceDetailPaymentInnerItemLayoutBinding
import com.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.dubaiculture.utils.hide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class PaymentPageFragment(val payments: List<Payment>?) : BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= ItemsServiceDetailInnerListingLayoutBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycling()
    }
    fun initRecycling(){
        binding.innerRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
            adapter = paymentInnerAdapter

            payments?.get(0)?.payments?.forEach {
//                val linearLayoutManager = LinearLayoutManager(context)
//                layoutManager = linearLayoutManager
//                val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
                binding.detailListingHeader.text =
                    context.getString(R.string.payments)

                adapter = paymentInnerAdapter
                val paymentsItem =
                    ServiceDetailListingItems<ItemsServiceDetailPaymentInnerItemLayoutBinding, PaymentX>(
                        eService = it,
                        resLayout = R.layout.items_service_detail_payment_inner_item_layout
                    )
                paymentInnerAdapter.add(paymentsItem)
//                val paymentsItem =
//                    ServiceDetailListingItems<ItemsServiceDetailInnerListingLayoutBinding, Payment>(
//                        eService = it,
//                        resLayout = R.layout.items_service_detail_inner_listing_layout
//                    )
//                paymentInnerAdapter.add(paymentsItem)
            }
//            if (payments?.get(0)?.payments!!.isEmpty()){
//                hide()
//            }
        }

    }
}