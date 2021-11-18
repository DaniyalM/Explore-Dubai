package com.dubaiculture.ui.postLogin.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.search.local.SearchResultItem
import com.dubaiculture.databinding.ItemSearchResultLayoutBinding
import com.dubaiculture.utils.AppConfigUtils.setAnimation
import com.dubaiculture.utils.hide

class SearchItemListAdapter(
    val searchItemClickListner: SearchItemClickListner
) :
    PagingDataAdapter<SearchResultItem, SearchItemListAdapter.SearchItemListViewHolder>(
        object : DiffUtil.ItemCallback<SearchResultItem>() {
            override fun areItemsTheSame(
                oldItem: SearchResultItem,
                newItem: SearchResultItem
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: SearchResultItem,
                newItem: SearchResultItem
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }
    ) {

    inner class SearchItemListViewHolder(
        val binding: ItemSearchResultLayoutBinding,
        val searchItemClickListner: SearchItemClickListner
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchResultItem: SearchResultItem) {
            setAnimation(binding.itemView,binding.root.context)
            binding.apply {
                item = searchResultItem
                if (searchResultItem.type.isEmpty())
                    tvSearchCat.hide()
                itemView.setOnClickListener {
                    searchItemClickListner.onSearchItemClick(searchResultItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchItemListViewHolder(
        ItemSearchResultLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        searchItemClickListner
    )

    override fun onBindViewHolder(holder: SearchItemListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    interface SearchItemClickListner {
        fun onSearchItemClick(searchResultItem: SearchResultItem)
    }
}