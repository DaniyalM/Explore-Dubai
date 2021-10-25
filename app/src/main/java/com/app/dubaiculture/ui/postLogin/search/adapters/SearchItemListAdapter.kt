package com.app.dubaiculture.ui.postLogin.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.search.local.SearchResultItem
import com.app.dubaiculture.databinding.ItemSearchResultLayoutBinding
import com.app.dubaiculture.utils.hide
import com.app.dubaiculture.utils.loadImage
import com.app.dubaiculture.utils.show

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

            binding.apply {
                itemView.setOnClickListener {
                    searchItemClickListner.onSearchItemClick(searchResultItem)
                }

                IvSearch.loadImage(searchResultItem.image)
                if (searchResultItem.type.isNotEmpty()){
                    tvSearchCat.text = searchResultItem.type
                }
                else
                tvSearchCat.hide()

                tvSearchTitle.text = searchResultItem.title
                tvSearchDate.text = searchResultItem.itemDate
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