package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.FAQ
import com.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.dubaiculture.databinding.ItemsServiceDetailInnerListingLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServiceProcedureListAdapter
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show

class ProcedurePageFragment :
    BaseFragment<ItemsServiceDetailInnerListingLayoutBinding>() {
    private lateinit var procedure: List<Procedure>
    private lateinit var serviceProcedureListAdapter: ServiceProcedureListAdapter

    companion object {
        fun newInstance(procedure: List<Procedure>): ProcedurePageFragment {
            val args = Bundle()
            args.putParcelableArrayList(Constants.NavBundles.PROCEDURE_LIST, ArrayList(procedure))
            val fragment = ProcedurePageFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            procedure= it.getParcelableArrayList(Constants.NavBundles.PROCEDURE_LIST)!!
        }
    }
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