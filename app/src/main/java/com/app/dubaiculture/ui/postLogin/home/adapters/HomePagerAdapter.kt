package com.app.dubaiculture.ui.postLogin.home.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.ui.postLogin.attractions.AttractionsFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.AttractionDetailFragment
import com.app.dubaiculture.ui.postLogin.events.EventFilterFragment
import com.app.dubaiculture.ui.postLogin.events.EventsFragment
import com.app.dubaiculture.ui.postLogin.explore.ExploreFragment
import com.app.dubaiculture.ui.postLogin.more.MoreFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ExploreFragment()
            }
            1 -> {
                EventsFragment()
            }
            2 -> {
                AttractionsFragment()
            }
            else -> {
                EventFilterFragment()
            }
        }
    }
}