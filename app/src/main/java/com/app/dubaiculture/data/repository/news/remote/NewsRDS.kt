package com.app.dubaiculture.data.repository.news.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.news.service.NewsService
import javax.inject.Inject

class NewsRDS @Inject constructor(val newsService: NewsService) : BaseRDS() {
}