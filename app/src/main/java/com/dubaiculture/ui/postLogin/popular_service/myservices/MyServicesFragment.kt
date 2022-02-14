package com.dubaiculture.ui.postLogin.popular_service.myservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentMyServicesBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.myservices.adapters.ServiceStatusListItem
import com.dubaiculture.ui.postLogin.popular_service.myservices.myServicesViewModel.MyServicesViewModel
import com.dubaiculture.utils.Constants
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyServicesFragment : BaseFragment<FragmentMyServicesBinding>() {
    private val myServicesViewModel: MyServicesViewModel by viewModels()

    private lateinit var linearLayoutManger: LinearLayoutManager
    private var serviceStatusAdapter: GroupAdapter<GroupieViewHolder>? = GroupAdapter()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMyServicesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customTextView3.text = activity.resources.getString(R.string.my_services)
        binding.headerVisited.back.setOnClickListener {
            back()
        }
        backArrowRTL(binding.headerVisited.back)
        initServiceRvListing()
        subscribeUiEvents(myServicesViewModel)
        subscribeToObservables()
        myServicesViewModel.fetchServiceStatusList(if (isArabic()) Constants.Locale.ARABIC else Constants.Locale.ENGLISH)
    }

    private fun initServiceRvListing() {
        linearLayoutManger = LinearLayoutManager(activity)
        binding.rvServiceStatus.apply {
            layoutManager = linearLayoutManger
            adapter = serviceStatusAdapter
        }
    }

    private fun subscribeToObservables() {
        myServicesViewModel.serviceStatusList.observe(viewLifecycleOwner, {
            serviceStatusAdapter?.addAll(it.map {
                ServiceStatusListItem(
                    serviceStatus = it,
                    context = requireContext()
                )
            })
        })
    }

}