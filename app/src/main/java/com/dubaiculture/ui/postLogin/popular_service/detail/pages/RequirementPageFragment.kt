package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.RequiredDocument
import com.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.dubaiculture.databinding.ItemsServiceDetailReqDocumentLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class  RequirementPageFragment(val requiredDocument: List<RequiredDocument>?) : BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
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
            binding.detailListingHeader.text =
                context.getString(R.string.required_documents)


            if (requiredDocument!!.get(0).requiredDocuments.isEmpty()){
                binding.noDataPlaceHolder.show()
                hide()
                binding.detailListingHeader.hide()

            }else{
                binding.noDataPlaceHolder.hide()
                val linearLayoutManager = LinearLayoutManager(context)
                layoutManager = linearLayoutManager
                val requiredDocumentInnerAdapter = GroupAdapter<GroupieViewHolder>()
                adapter = requiredDocumentInnerAdapter
                requiredDocument.get(0).requiredDocuments.forEach {
                    val paymentsItem =
                        ServiceDetailListingItems<ItemsServiceDetailReqDocumentLayoutBinding, String>(
                            eService = it,
                            resLayout = R.layout.items_service_detail_req_document_layout
                        )
                    requiredDocumentInnerAdapter.add(paymentsItem)
                }
            }


        }

    }
}