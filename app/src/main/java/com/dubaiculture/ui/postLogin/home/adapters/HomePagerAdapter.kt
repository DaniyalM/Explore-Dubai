package com.dubaiculture.ui.postLogin.home.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dubaiculture.ui.postLogin.attractions.AttractionsFragment
import com.dubaiculture.ui.postLogin.attractions.detail.AttractionDetailFragment
import com.dubaiculture.ui.postLogin.events.EventFilterFragment
import com.dubaiculture.ui.postLogin.events.EventsFragment
import com.dubaiculture.ui.postLogin.explore.ExploreFragment

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