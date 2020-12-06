package com.daniyal.basicappimpl.ui.postLogin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.daniyal.basicappimpl.databinding.FragmentHomeBinding
import com.daniyal.basicappimpl.ui.base.BaseFragment
import com.daniyal.basicappimpl.ui.postLogin.home.viewmodels.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

//    private fun initViewPager() {
//        binding.pager.adapter = HomePagerAdapter(this)
//        TabLayoutMediator(
//            binding.tabLayout, binding.pager
//        ) { tab: TabLayout.Tab, position: Int ->
//            val tabDetail = homeViewModel.tabDetails[position]
//            tab.text = getString(tabDetail.first)
//            tab.icon = ResourcesCompat.getDrawable(resources, tabDetail.second, null)
//        }.attach()
//    }
}