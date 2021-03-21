package com.app.dubaiculture.ui.postLogin.events.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.events.EventListingFragment

class EventPagerAdapter(fragment: Fragment, private val eventID : Int) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int) =
                EventListingFragment.newInstance(eventID =  eventID)


}