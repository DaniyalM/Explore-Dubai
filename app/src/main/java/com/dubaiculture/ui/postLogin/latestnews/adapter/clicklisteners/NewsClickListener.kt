package com.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners

import android.widget.ImageView
import com.dubaiculture.data.repository.news.local.LatestNews

interface NewsClickListener {
    fun rowClickListener(news: LatestNews)
    fun rowClickListener(news: LatestNews,position: Int,)
}