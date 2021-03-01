package com.app.dubaiculture.ui.postLogin.attractions.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.events.EventsFragment
import com.app.dubaiculture.ui.postLogin.more.MoreFragment

class AttractionPagerAdaper(fragment: Fragment,val attractionCategory: ArrayList<AttractionCategory>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 9

    override fun createFragment(position: Int): Fragment {
        return    when (position >= attractionCategory.size) {
           false-> {
               AttractionListingFragment.newInstance(attractionCategory.get(position).attractions)
           }
            else -> {
                AttractionListingFragment.newInstance(attractionCategory.get(attractionCategory.size-1).attractions)
            }
        }


    }
}