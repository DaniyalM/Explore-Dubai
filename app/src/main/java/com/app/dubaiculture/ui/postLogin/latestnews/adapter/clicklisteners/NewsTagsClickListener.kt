package com.app.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners

import com.app.dubaiculture.data.repository.news.local.NewsTags

interface NewsTagsClickListener {
    fun onTagSelectListner(newsTags: NewsTags)
}