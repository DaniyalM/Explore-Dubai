package com.dubaiculture.ui.postLogin.latestnews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.news.local.LatestNews
import com.dubaiculture.databinding.ItemsLatestNewsBinding
import com.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsClickListener

class LatestNewsListAdapter(val rowClickListener: NewsClickListener) :
    ListAdapter<LatestNews, LatestNewsListAdapter.LatestNewsViewHolder>(LatestNewsDiffCallback()) {

    inner class LatestNewsViewHolder(
        val binding: ItemsLatestNewsBinding,
        val rowClickListener: NewsClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: LatestNews) {
            binding.news = news
            binding.materialCardView.setOnClickListener {
                rowClickListener.rowClickListener(news)
                rowClickListener.rowClickListener(news, absoluteAdapterPosition)
            }
        }
    }

    class LatestNewsDiffCallback : DiffUtil.ItemCallback<LatestNews>() {
        override fun areItemsTheSame(
            oldItem: LatestNews,
            newItem: LatestNews
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: LatestNews,
            newItem: LatestNews
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestNewsViewHolder {
        return LatestNewsViewHolder(
            ItemsLatestNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: LatestNewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}