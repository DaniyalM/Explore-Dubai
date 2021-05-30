package com.app.dubaiculture.data.repository.news

import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.news.remote.NewsRDS
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRDS: NewsRDS): BaseRepository(newsRDS) {}