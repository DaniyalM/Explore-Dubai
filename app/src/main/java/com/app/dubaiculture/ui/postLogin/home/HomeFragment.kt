package com.app.dubaiculture.ui.postLogin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.databinding.FragmentHomeBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.home.viewmodels.HomeViewModel

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