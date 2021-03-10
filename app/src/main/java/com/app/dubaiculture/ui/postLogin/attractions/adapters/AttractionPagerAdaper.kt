package com.app.dubaiculture.ui.postLogin.attractions.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment

class AttractionPagerAdaper(fragment: Fragment, val attractionId: String = "") :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 9

    override fun createFragment(position: Int) =
        AttractionListingFragment.newInstance(attractionId = attractionId)
}


//when (position >= attractionCategory.size) {
//    false-> {
////               AttractionListingFragment.newInstance(attractionCategory[position].attractions)
//    }
//    else -> {
////                AttractionListingFragment.newInstance(attractionCategory[attractionCategory.size-1].attractions)
//    }
//}