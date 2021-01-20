package com.app.dubaiculture.ui.postLogin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.app.dubaiculture.databinding.FragmentHomeBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.ExploreFragment
import com.app.dubaiculture.ui.postLogin.home.adapters.HomePagerAdapter
import com.app.dubaiculture.ui.postLogin.home.viewmodels.HomeViewModel
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
        initViewPager()
    }

    private fun initViewPager() {
        binding.pager.adapter = HomePagerAdapter(this)
        TabLayoutMediator(
            binding.tabLayout, binding.pager
        ) { tab: TabLayout.Tab, position: Int ->
            val tabDetail = homeViewModel.tabDetails[position]
            tab.text = getString(tabDetail.first)
            tab.icon = ResourcesCompat.getDrawable(resources, tabDetail.second, null)
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.apply {
                    val f = childFragmentManager.findFragmentByTag(
                        "f" + (binding.pager.adapter as HomePagerAdapter).getItemId(this.position)
                    )//f0,f1,f2,f3,....
                    if (f != null) {
                        if (this.position == 0) {
                            val fragment: ExploreFragment = f as ExploreFragment
                            fragment.getRecyclerView().smoothScrollToPosition(0)
                        }
                    }
                }

            }

        })
    }
}