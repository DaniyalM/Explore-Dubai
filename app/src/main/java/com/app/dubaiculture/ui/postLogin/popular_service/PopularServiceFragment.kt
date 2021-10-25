package com.app.dubaiculture.ui.postLogin.popular_service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.EService
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.app.dubaiculture.databinding.FragmentPopularServiceBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListAdapter
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.clicklistener.ServiceClickListner
import com.app.dubaiculture.ui.postLogin.popular_service.models.ServiceHeader
import com.app.dubaiculture.ui.postLogin.popular_service.viewmodels.PopularServiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularServiceFragment : BaseFragment<FragmentPopularServiceBinding>(), View.OnClickListener {
    private val popularServiceViewModel: PopularServiceViewModel by viewModels()
    private lateinit var popularServiceListAdapter: PopularServiceListAdapter
    private var sCat: List<ServiceCategory>? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPopularServiceBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.viewmodel = popularServiceViewModel
        subscribeUiEvents(popularServiceViewModel)
        rvSetup()
        subscribeToObservable()
        binding.imgCancel.setOnClickListener(this)
        binding.headerVisited.back.setOnClickListener(this)
    }

    private fun rvSetup() {
        binding.rvServiceListing.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            popularServiceListAdapter = PopularServiceListAdapter(object : ServiceClickListner {
                override fun onServiceClick(service: EService?) {
                    navigateByDirections(
                        PopularServiceFragmentDirections.actionPopularServiceFragmentToServiceDetailNavigation(
                            service!!.id
                        )
                    )
                }
            })

            adapter = popularServiceListAdapter
        }
    }

    private fun subscribeToObservable() {

        popularServiceViewModel.serviceListCategory.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                sCat = it
                binding.horizontalSelector.initialize(initializeHeaders(it))

            }
        }
        popularServiceViewModel.serviceList.observe(viewLifecycleOwner) {
            popularServiceListAdapter.submitList(it)

        }
        binding.horizontalSelector.headerPosition.observe(viewLifecycleOwner) { pos ->
            if (sCat != null) {
                popularServiceViewModel.id = sCat!![pos].id
                popularServiceViewModel.updateServiceList(
                    popularServiceViewModel.id
                )
            }

        }


    }


    private fun initializeHeaders(headers: List<ServiceCategory>): MutableList<ServiceHeader> {
        val serviceListingHeader = ArrayList<ServiceHeader>()
        headers.forEach {
            serviceListingHeader.add(
                ServiceHeader(
                    title = it.title,
                    selectedIcon = null, unselectedIcon = null,
                    selectedIconString = it.hoverIcon,
                    unSelectedIconString = it.normalIcon
                )
            )
        }


        return serviceListingHeader
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_cancel -> {
                hideKeyboard(activity)
                binding.editSearchServices.text?.clear()
            }
            R.id.back -> {
                back()
            }
        }
    }


}