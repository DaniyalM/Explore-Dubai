package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.Description
import com.app.dubaiculture.data.repository.popular_service.local.models.Payment
import com.app.dubaiculture.data.repository.popular_service.local.models.PaymentX
import com.app.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.app.dubaiculture.databinding.*
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem

class ServiceDetailListingItems<T : ViewDataBinding, out D>(
    private val rowClickListener: RowClickListener? = null,
    val eService: D? = null,
    val resLayout: Int = R.layout.items_service_detail_desc_layout,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsServiceDetailDescLayoutBinding -> {
                if (eService is Description) {
                    viewBinding.description = eService

                }
            }

            is ItemsServiceDetailInnerListingLayoutBinding -> {
                viewBinding.innerRecyclerView.apply {
                    when(eService){
                        is Payment ->{
                            val linearLayoutManager = LinearLayoutManager(context)
                            layoutManager = linearLayoutManager
                            val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
                            adapter = paymentInnerAdapter
                            eService.payments.forEach {
                                val paymentsItem =
                                    ServiceDetailListingItems<ItemsServiceDetailPaymentInnerItemLayoutBinding, PaymentX>(
                                        eService = it,
                                        resLayout = R.layout.items_service_detail_payment_inner_item_layout
                                    )
                                paymentInnerAdapter.add(paymentsItem)
                            }
                        }
                    }

                }
            }

            is ItemsServiceDetailProcedureLayoutBinding -> {
                if (eService is Procedure) {
                    viewBinding.procedure = eService
                }
            }
            is ItemsServiceDetailPaymentInnerItemLayoutBinding -> {
                if (eService is Payment) {
                    viewBinding.payment = eService
                }
            }
        }


    }

    override fun getLayout() = resLayout

}