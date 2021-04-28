package com.app.dubaiculture.ui.postLogin.latestnews.adapter

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.LatestNews
import com.app.dubaiculture.databinding.LatestNewsInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

class LatestNewsListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val news: LatestNews? = null,
    val resLayout: Int = R.layout.latest_news_inner_item_cell,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is LatestNewsInnerItemCellBinding -> {
                viewBinding.news = news
            }

        }
    }
}

