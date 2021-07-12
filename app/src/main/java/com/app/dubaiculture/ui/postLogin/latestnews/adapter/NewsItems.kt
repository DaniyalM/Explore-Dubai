package com.app.dubaiculture.ui.postLogin.latestnews.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.databinding.*
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.glideInstance
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.xwray.groupie.databinding.BindableItem

data class NewsItems <T : ViewDataBinding>(
        private val rowClickListener: RowClickListener? = null,
        val latestNews: LatestNews,
        val resLayout: Int = R.layout.items_latest_news,
        val context: Context,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsLatestNewsBinding ->{
                viewBinding.let {
                    it.news=latestNews
                    it.materialCardView.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                    }
                }
            }
            is ItemsNewsVerticalLayoutBinding -> {
                viewBinding.let {
                    it.news=latestNews
                    it.materialCardView.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                    }
                }
            }
            is ItemNewsArticleBinding ->{
                viewBinding.let {
                    it.tvTitleCategory.text = latestNews.title
                }
            }
            is ItemMoreNewsBinding -> {
                viewBinding.let {

                    it.news=latestNews
                }
            }
            is LatestNewsInnerItemCellBinding -> {
                viewBinding.news = latestNews
                viewBinding.cardview.setOnClickListener {
                    rowClickListener?.rowClickListener(position)
                }
            }

        }
    }

    override fun getLayout() = resLayout


}