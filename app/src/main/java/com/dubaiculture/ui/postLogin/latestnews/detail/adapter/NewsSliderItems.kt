package com.dubaiculture.ui.postLogin.latestnews.detail.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.news.local.LatestNews
import com.dubaiculture.data.repository.news.local.NewsDetail
import com.dubaiculture.data.repository.news.local.TwoColumnImageModule
import com.dubaiculture.databinding.ItemMoreNewsBinding
import com.dubaiculture.databinding.ItemNewsArticleBinding
import com.dubaiculture.databinding.ItemSliderBinding
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.glideInstance
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.xwray.groupie.databinding.BindableItem
data class NewsSliderItems <T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val newsDetail: NewsDetail,
    val resLayout: Int = R.layout.item_slider,
    val context: Context,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemSliderBinding ->{
                viewBinding.let {
                    YoYo.with(Techniques.BounceInUp)
                        .duration(1000)
                        .playOn(it.root)
                        it.imgSlider.glideInstance(newsDetail.image, false).into(it.imgSlider)

                }
            }
            is ItemNewsArticleBinding->{
                viewBinding.let {
                    YoYo.with(Techniques.BounceInUp)
                        .duration(1000)
                        .playOn(it.root)
                    newsDetail.tags?.forEach {items->
                        it.tvTitleCategory.text = items
                    }
                }
            }
        }
    }
    override fun getLayout() = resLayout
}