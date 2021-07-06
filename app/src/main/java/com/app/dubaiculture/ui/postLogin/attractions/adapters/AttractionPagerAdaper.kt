package com.app.dubaiculture.ui.postLogin.attractions.adapters

import androidx.fragment.app.Fragment
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.ui.base.recyclerstuf.BaseFragmentStateAdapter
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment


class AttractionPagerAdaper(fragment: Fragment) : BaseFragmentStateAdapter<AttractionCategory>(fragment) {

    override fun getItemCount() = list.size
    var list: List<AttractionCategory>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun createFragment(position: Int) =
            AttractionListingFragment.newInstance(attractionCat = list[position])

}


