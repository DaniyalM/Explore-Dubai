package com.app.dubaiculture.ui.postLogin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentHomeBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.home.adapters.HomePagerAdapter
import com.app.dubaiculture.ui.postLogin.home.viewmodels.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    private var bottomNavigationView: BottomNavigationView? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomNavigationView = binding.bottomNav
        initBottomNavigation()
//        initViewPager()
    }

    private fun initBottomNavigation(){
        binding.pager.adapter = HomePagerAdapter(this)
        binding.pager.isUserInputEnabled = false
//        binding.bottomNav.setupWithViewPager(binding.pager as ViewPager)
        bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_explore -> binding.pager.currentItem = 0
                R.id.action_events -> binding.pager.currentItem = 1
                R.id.action_forums -> binding.pager.currentItem = 2
                else -> binding.pager.currentItem = 3
            }
            true
        }
    }

    private fun initViewPager() {



//        TabLayoutMediator(
//            binding.tabLayout, binding.pager
//        ) { tab: TabLayout.Tab, position: Int ->
//            val tabDetail = homeViewModel.tabDetails[position]
//            tab.text = getString(tabDetail.first)
//            tab.icon = ResourcesCompat.getDrawable(resources, tabDetail.second, null)
//        }.attach()
//
//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                tab?.apply {
//                    val f = childFragmentManager.findFragmentByTag(
//                        "f" + (binding.pager.adapter as HomePagerAdapter).getItemId(this.position)
//                    )//f0,f1,f2,f3,....
//                    if (f != null) {
//                        if (this.position == 0) {
//                            val fragment: ExploreFragment = f as ExploreFragment
//                            fragment.getRecyclerView().smoothScrollToPosition(0)
//                        }
//                    }
//                }
//
//            }
//
//        })
    }
}