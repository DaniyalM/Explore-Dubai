package com.app.dubaiculture.ui.postLogin.popular_service.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.app.dubaiculture.databinding.CustomTabLayoutBinding
import com.app.dubaiculture.databinding.FragmentServiceDetailFragmentBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.detail.adapters.ServiceHeaderPagerAdapter
import com.app.dubaiculture.ui.postLogin.popular_service.detail.viewmodels.ServiceDetailViewModel
import com.app.dubaiculture.utils.handleApiError
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
        subscribeUiEvents(serviceDetailViewModel)
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            binding.swipeRefreshLayout.isRefreshing = false
//        }
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        serviceDetailViewModel.eServicesDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    initViewPager(it.value)
//                    binding.horizontalSelector.initializeSelector(it.value)
                }
                is Result.Failure -> handleApiError(it, serviceDetailViewModel)

            }
        }


    }


    private fun initViewPager(eServicesDetail: EServicesDetail) {

        binding.forumPager.adapter = ServiceHeaderPagerAdapter(this, eServicesDetail)
        binding.forumPager.isUserInputEnabled = false
        binding.forumPager.isSaveEnabled = false
        TabLayoutMediator(
            binding.tabLayout, binding.forumPager
        ) { tab: TabLayout.Tab, position: Int ->
            val tabTitle = TabHeaders.fromId(position).name
            val v: CustomTabLayoutBinding = CustomTabLayoutBinding.inflate(layoutInflater)
            v.tabTitle.text = tabTitle
            tab.customView = v.root

        }.attach()

//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val view = tab?.customView
//                val imageView = view?.findViewById<ImageView>(R.id.tab_icon)
//                imageView?.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        activity,
//                        R.drawable.bg_blue_shape
//                    )
//                )
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                val view = tab?.customView
//                val imageView = view?.findViewById<ImageView>(R.id.tab_icon)
//                imageView?.setImageResource(tabIcons[tab.position])
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//        })
    }


}