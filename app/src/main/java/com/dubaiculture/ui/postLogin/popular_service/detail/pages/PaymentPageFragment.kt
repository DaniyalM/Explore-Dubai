package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.Payment
import com.dubaiculture.data.repository.popular_service.local.models.PaymentX
import com.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.dubaiculture.databinding.ItemsServiceDetailPaymentInnerItemLayoutBinding
import com.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class PaymentPageFragment : BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= ItemsServiceDetailInnerListingLayoutBinding.inflate(inflater,container,false)

    private lateinit var  payments: List<Payment>
    companion object {
        fun newInstance(payments: List<Payment>): PaymentPageFragment {
            val args = Bundle()
            args.putParcelableArrayList(Constants.NavBundles.PAYMENT_LIST, ArrayList(payments))
            val fragment = PaymentPageFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            payments= it.getParcelableArrayList(Constants.NavBundles.PAYMENT_LIST)!!
        }
    }
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
            if (payments?.get(0)?.payments!!.isEmpty()){
                binding.noDataPlaceHolder.show()
                binding.detailListingHeader.hide()

                hide()

            }else {
                payments.get(0).payments.forEach {
                    binding.detailListingHeader.text =
                        context.getString(R.string.payments)
                    adapter = paymentInnerAdapter
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