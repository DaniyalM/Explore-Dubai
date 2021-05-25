package com.app.dubaiculture.ui.postLogin.more.news.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.LatestNews
import com.app.dubaiculture.databinding.ItemMoreNewsBinding
import com.app.dubaiculture.databinding.ItemsNewsVerticalLayoutBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.glideInstance
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.xwray.groupie.databinding.BindableItem

data class NewsVerticalItems<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val latestNews: LatestNews,
    val resLayout: Int = R.layout.items_news_vertical_layout,
    val context: Context,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsNewsVerticalLayoutBinding -> {
                viewBinding.let {
                    YoYo.with(Techniques.BounceInUp)
                        .duration(1000)
                        .playOn(it.root)
                    it.imgNewsVertical.glideInstance(latestNews.image, false)
                        .into(it.imgNewsVertical)
                    it.tvTitle.text = latestNews.title
                    it.tvDate.text = latestNews.date
                    it.materialCardView.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                    }
                }
            }
            is ItemMoreNewsBinding -> {
                viewBinding.let {
                    it.imgUpcomingBg.glideInstance(latestNews.image, false).into(it.imgUpcomingBg)
                    it.title.text = latestNews.title.toString()
                    it.date.text = latestNews.date
                }
            }
        }
    }

    override fun getLayout() = resLayout


}