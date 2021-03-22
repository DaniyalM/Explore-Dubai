package com.app.dubaiculture.ui.postLogin.events.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.events.EventListingFragment

class EventPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private lateinit var list: List<EventHomeListing>


    override fun getItemCount() = list.size
    fun provideListToPager(list: List<EventHomeListing>) {
        this.list = list
    }
    override fun createFragment(position: Int) =
                EventListingFragment.newInstance(eventID =  list[position].category)
}