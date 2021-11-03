package com.app.dubaiculture.ui.postLogin.popular_service.detail

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.app.dubaiculture.databinding.CustomTabLayoutBinding
import com.app.dubaiculture.databinding.FragmentServiceDetailFragmentBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.detail.adapters.ServiceHeaderPagerAdapter
import com.app.dubaiculture.ui.postLogin.popular_service.detail.viewmodels.ServiceDetailViewModel
import com.app.dubaiculture.utils.getColorFromAttr
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.show
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ServiceDetailFragment : BaseFragment<FragmentServiceDetailFragmentBinding>() {
    private val serviceDetailViewModel: ServiceDetailViewModel by viewModels()
    private val serviceDetailFragmentArgs: ServiceDetailFragmentArgs by navArgs()

    //    private var serviceId: String = "89F321A2034E49AEACE41865CD5862DA"
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServiceDetailFragmentBinding.inflate(inflater, container, false)


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(serviceDetailViewModel)
        backArrowRTL(binding.headerVisited.back)
        binding.headerVisited.favouritetemp.show()
        binding.headerVisited.favourite.show()
        binding.headerVisited.back.setOnClickListener {
            back()
        }
        binding.headerVisited.favourite.setOnClickListener {

            favouriteClick(
                binding.headerVisited.favourite,
                true,
                R.id.action_serviceDetailFragment2_to_post_login_bottom_navigation,
                serviceDetailFragmentArgs.serviceId,
                serviceDetailViewModel,
                3
            )

        }
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
                }
                is Result.Failure -> handleApiError(it, serviceDetailViewModel)

            }
        }

        serviceDetailViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {

                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        binding.headerVisited.favourite.background =
                            getDrawableFromId(R.drawable.heart_icon_fav)

                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_fav)

                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        binding.headerVisited.favourite.background =
                            getDrawableFromId(R.drawable.heart_icon_home_black)
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_home_black)

                    }
                }
                is Result.Failure -> handleApiError(it, serviceDetailViewModel)
            }
        }


    }


    private fun initViewPager(eServicesDetail: EServicesDetail) {
        if (eServicesDetail.is_favourite) {
            binding.headerVisited.favourite.background =
                getDrawableFromId(R.drawable.heart_icon_fav)
        }
        binding.forumPager.adapter =
            ServiceHeaderPagerAdapter(this, eServicesDetail, binding.forumPager)
//        binding.forumPager.isUserInputEnabled = false
        binding.forumPager.isSaveEnabled = false
        TabLayoutMediator(
            binding.tabLayout, binding.forumPager
        ) { tab: TabLayout.Tab, position: Int ->
            var tabTitle = TabHeaders.fromId(position).name
            val v: CustomTabLayoutBinding = CustomTabLayoutBinding.inflate(layoutInflater)

            when (tabTitle) {
                TabHeaders.DESCRIPTION.name -> {
                    tabTitle = activity.resources.getString(R.string.description)
                    v.tabTitle.setTextColor(activity.getColorFromAttr(R.attr.colorSecondary))


                }
                TabHeaders.PROCEDURE.name -> {
                    tabTitle = activity.resources.getString(R.string.procedure)

                }
                TabHeaders.REQUIREDDOCUMENTS.name -> {
                    tabTitle = activity.resources.getString(R.string.required_documents)

                }
                TabHeaders.PAYMENTS.name -> {
                    tabTitle = activity.resources.getString(R.string.payments)
                }
                TabHeaders.FAQS.name -> {
                    tabTitle = activity.resources.getString(R.string.faqs)

                }
                TabHeaders.TERMSANDCONDITIONS.name -> {
                    tabTitle = activity.resources.getString(R.string.terms_and_conditions)
                }
                else -> {
                    tabTitle = activity.resources.getString(R.string.start_service)
                }
            }
            v.tabTitle.text = tabTitle

            tab.customView = v.root

        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.apply {
                    val title = findViewById<TextView>(R.id.tab_title)
                    title.setTextColor(activity.getColorFromAttr(R.attr.colorSecondary))

                    tab.customView = this
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.apply {
                    val title = findViewById<TextView>(R.id.tab_title)
                    title.setTextColor(ContextCompat.getColor(activity, R.color.gray_400))

                    tab.customView = this
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


    }


}