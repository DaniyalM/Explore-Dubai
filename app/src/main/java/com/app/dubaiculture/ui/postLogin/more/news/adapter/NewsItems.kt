package com.app.dubaiculture.ui.postLogin.more.news.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.LatestNews
import com.app.dubaiculture.data.repository.notification.NotificationModel
import com.app.dubaiculture.databinding.ItemsLatestNewsBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.glideInstance
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
                    it.imgNews.glideInstance(latestNews.image, false).into(it.imgNews)
                    it.tvTitle.text = latestNews.title
                    it.tvDate.text = latestNews.date
                    it.materialCardView.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                    }
                }
            }
        }
    }

    override fun getLayout() = resLayout


}