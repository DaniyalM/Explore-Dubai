package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment


class AttractionPagerAdaper(fragment: Fragment) : FragmentStateAdapter(fragment) {


    private val diffCallback = object : DiffUtil.ItemCallback<AttractionCategory>() {
        override fun areItemsTheSame(oldItem: AttractionCategory, newItem: AttractionCategory): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: AttractionCategory, newItem: AttractionCategory): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    protected val differ = AsyncListDiffer(this, diffCallback)


    override fun getItemCount() = list.size

    //    fun provideListToPager(list: List<AttractionCategory>) {
//        this.list = list
//    }
    var list: List<AttractionCategory>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun createFragment(position: Int) = AttractionListingFragment.newInstance(attractionCat = list.get(position))



}


