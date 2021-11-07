package com.dubaiculture.ui.postLogin.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.databinding.SearchHistoryItemLayoutBinding

class SearchHistoryAdapter(val searchHistoryClick: SearchHistoryClick) :
    ListAdapter<String, SearchHistoryAdapter.SearchHistoryViewHolder>(object :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }) {
    inner class SearchHistoryViewHolder(
        val binding: SearchHistoryItemLayoutBinding,
        val searchHistoryClick: SearchHistoryClick
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.apply {
                query.text = text
                historyItem.setOnClickListener {
                    searchHistoryClick.onClick(text)
                }
            }

        }
    }

    interface SearchHistoryClick {
        fun onClick(query: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(
            SearchHistoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            searchHistoryClick = searchHistoryClick
        )
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

