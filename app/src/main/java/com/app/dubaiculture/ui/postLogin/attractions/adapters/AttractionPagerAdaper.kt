package com.app.dubaiculture.ui.postLogin.attractions.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment

class AttractionPagerAdaper(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private lateinit var list: List<AttractionCategory>


    override fun getItemCount() = list.size
    fun provideListToPager(list: List<AttractionCategory>) {
        this.list = list
    }

    override fun createFragment(position: Int) = AttractionListingFragment.newInstance(attractionCatId = list.get(position).id!!)

}


