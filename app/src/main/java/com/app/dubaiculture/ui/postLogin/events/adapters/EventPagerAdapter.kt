package com.app.dubaiculture.ui.postLogin.events.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.events.EventListingFragment

class EventPagerAdapter(fragment: Fragment, val eventsCategory: ArrayList<EventHomeListing>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 9

    override fun createFragment(position: Int): Fragment {
        return when (position >= eventsCategory.size) {
            false -> {
                EventListingFragment.newInstance(eventsCategory[position].events as ArrayList<Events>)
            }
            else -> {
                EventListingFragment.newInstance(eventsCategory[eventsCategory.size - 1].events as ArrayList<Events>)
            }
        }


    }
}