package com.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners

import com.dubaiculture.data.repository.news.local.NewsTags

interface NewsTagsClickListener {
    fun onTagSelectListner(newsTags: NewsTags)
}