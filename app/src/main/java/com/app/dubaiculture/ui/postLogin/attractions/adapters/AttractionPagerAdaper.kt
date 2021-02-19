package com.app.dubaiculture.ui.postLogin.attractions.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.events.EventsFragment
import com.app.dubaiculture.ui.postLogin.more.MoreFragment

class AttractionPagerAdaper(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 9

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                AttractionListingFragment.newInstance(type = "Museum")
            }
            1 -> {

                AttractionListingFragment.newInstance(type = "Heritage Sites")
            }
            2 -> {

                AttractionListingFragment.newInstance(type = "Festivals")
            }
            else -> {

                AttractionListingFragment.newInstance(type = "Attractions")
            }
        }
    }
}