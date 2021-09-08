package com.app.dubaiculture.ui.postLogin.latestnews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.databinding.LatestNewsInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsClickListener
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.comparators.NewsComparators

class NewsPagingAdapter(val rowClickListener: NewsClickListener) :
    PagingDataAdapter<LatestNews, NewsPagingAdapter.NewsViewHolder>(
        NewsComparators()
    ) {

    inner class NewsViewHolder(
        val binding: LatestNewsInnerItemCellBinding,
        val rowClickListener: NewsClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: LatestNews) {
            binding.news = news
            binding.cardview.setOnClickListener {
                rowClickListener.rowClickListener(news)
                rowClickListener.rowClickListener(news, absoluteAdapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LatestNewsInnerItemCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }
}