package com.app.dubaiculture.ui.postLogin.popular_service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.local.models.EService
import com.app.dubaiculture.data.repository.popular_service.local.models.EServices
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.app.dubaiculture.databinding.FragmentPopularServiceBinding
import com.app.dubaiculture.databinding.ItemsServiceListingLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListItem
import com.app.dubaiculture.ui.postLogin.popular_service.models.ServiceHeader
import com.app.dubaiculture.ui.postLogin.popular_service.viewmodels.PopularServiceViewModel
import com.app.dubaiculture.utils.handleApiError
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularServiceFragment : BaseFragment<FragmentPopularServiceBinding>() {
    private val popularServiceViewModel: PopularServiceViewModel by viewModels()
    private var groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var eServices: EServices? = null
    private var eServicesList = ArrayList<EService>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPopularServiceBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservable()
    }

    fun subscribeToObservable() {
        popularServiceViewModel.eServices.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    eServices = it.value
                    it.value.eServices.forEach {
                        eServicesList.add(it)
                    }
                    binding.horizontalSelector.initialize(initializeHeaders(eServices!!.serviceCategory))
                }
                is Result.Failure -> handleApiError(it, popularServiceViewModel)
            }
        }
        binding.horizontalSelector.headerPosition.observe(viewLifecycleOwner) {
            groupAdapter.clear()
            getCategoryID(eServices?.serviceCategory?.get(it)?.id?:"89F321A2034E49AEACE41865CD5862DA")
        }
    }

    private fun getCategoryID(categoryID: String) {
        popularServiceViewModel.returnFilterList(list = eServicesList).map {
            groupAdapter.add(PopularServiceListItem<ItemsServiceListingLayoutBinding>(
                eService = it,
                resLayout = R.layout.items_service_listing_layout
            ))
        }
        binding.rvServiceListing.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter

        }
    }

    private fun initializeHeaders(headers: List<ServiceCategory>): MutableList<ServiceHeader> {
        val serviceListingHeader = ArrayList<ServiceHeader>()
        headers.forEach {
            serviceListingHeader.add(
                ServiceHeader(
                    title = it.title,
                    selectedIcon = null, unselectedIcon = null
                )
            )
        }


        return serviceListingHeader
    }


}