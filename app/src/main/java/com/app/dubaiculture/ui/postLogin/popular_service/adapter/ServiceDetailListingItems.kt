package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.*
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
                        is Procedure ->{
                            viewBinding.detailListingHeader.text =
                                context.getString(R.string.procedure)
                            val linearLayoutManager = LinearLayoutManager(context)
                            layoutManager = linearLayoutManager
                            val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
                            adapter = paymentInnerAdapter
                            eService.serviceProcedure.forEach {
                                val paymentsItem =
                                    ServiceDetailListingItems<ItemsServiceDetailProcedureLayoutBinding, ServiceProcedure>(
                                        eService = it,
                                        resLayout = R.layout.items_service_detail_procedure_layout
                                    )
                                paymentInnerAdapter.add(paymentsItem)
                            }
                        }

                        is Payment -> {

                            val linearLayoutManager = LinearLayoutManager(context)
                            layoutManager = linearLayoutManager
                            val paymentInnerAdapter = GroupAdapter<GroupieViewHolder>()
                            viewBinding.detailListingHeader.text =
                                context.getString(R.string.payments)
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
                        is RequiredDocument -> {
                            viewBinding.detailListingHeader.text =
                                context.getString(R.string.required_documents)
                            val linearLayoutManager = LinearLayoutManager(context)
                            layoutManager = linearLayoutManager
                            val requiredDocumentInnerAdapter = GroupAdapter<GroupieViewHolder>()
                            adapter = requiredDocumentInnerAdapter

                            repeat(5){
                                val paymentsItem =
                                    ServiceDetailListingItems<ItemsServiceDetailReqDocumentLayoutBinding, String>(
                                        eService = it.toString(),
                                        resLayout = R.layout.items_service_detail_req_document_layout
                                    )
                                requiredDocumentInnerAdapter.add(paymentsItem)
                            }
//                            eService.requiredDocuments.forEach {
//
//                            }
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