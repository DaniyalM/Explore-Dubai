package com.app.dubaiculture.ui.postLogin.latestnews.adapter.comparators

import androidx.recyclerview.widget.DiffUtil
import com.app.dubaiculture.data.repository.news.local.LatestNews

class NewsComparators : DiffUtil.ItemCallback<LatestNews>() {
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