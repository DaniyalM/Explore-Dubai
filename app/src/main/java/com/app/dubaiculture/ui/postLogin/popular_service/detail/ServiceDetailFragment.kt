package com.app.dubaiculture.ui.postLogin.popular_service.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.databinding.FragmentServiceDetailFragmentBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.detail.viewmodels.ServiceDetailViewModel
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceDetailFragment : BaseFragment<FragmentServiceDetailFragmentBinding>() {
    //    private lateinit var serviceObject: PopularServices
    private val serviceDetailViewModel: ServiceDetailViewModel by viewModels()
    private var serviceId: String = "89F321A2034E49AEACE41865CD5862DA"
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServiceDetailFragmentBinding.inflate(inflater, container, false)

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        arguments?.apply {
//           serviceObject = getParcelable(SERVICE_OBJECT)!!
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serviceDetailViewModel.getEServicesToScreen(getCurrentLanguage().language, serviceId)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
        }
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        serviceDetailViewModel.eServicesDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.horizontalSelector.initializeSelector(it.value)
                }
                is Result.Failure -> handleApiError(it, serviceDetailViewModel)

            }
        }

    }


}