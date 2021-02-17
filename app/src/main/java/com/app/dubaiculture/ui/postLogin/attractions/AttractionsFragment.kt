package com.app.dubaiculture.ui.postLogin.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.CustomTabLayoutViewBinding
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.explore.ExploreFragment
import com.app.dubaiculture.ui.postLogin.home.adapters.HomePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.custom_tab_layout_view.view.*


class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionsBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initViewPager()
    }

//    private fun initViewPager() {
//
//        binding.pager.adapter = AttractionPagerAdaper(this)
//
//
//        TabLayoutMediator(
//            binding.tabLayout, binding.pager
//        ) { tab: TabLayout.Tab, position: Int ->
//            val tabDetail = attractionViewModel.tabDetails[position]
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
//    }


}