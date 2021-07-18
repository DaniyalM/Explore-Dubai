package com.app.dubaiculture.ui.postLogin.popular_service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.app.dubaiculture.databinding.FragmentPopularServiceBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.models.ServiceHeader
import com.app.dubaiculture.ui.postLogin.popular_service.viewmodels.PopularServiceViewModel
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularServiceFragment : BaseFragment<FragmentPopularServiceBinding>() {
    private val popularServiceViewModel: PopularServiceViewModel by viewModels()
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
                    binding.horizontalSelector.initialize(initializeHeaders(it.value.serviceCategory))

                }
                is Result.Failure -> handleApiError(it, popularServiceViewModel)
            }
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