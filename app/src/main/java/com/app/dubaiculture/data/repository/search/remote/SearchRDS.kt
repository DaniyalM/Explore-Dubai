package com.app.dubaiculture.data.repository.search.remote

import com.app.dubaiculture.data.repository.base.BaseRDS
import com.app.dubaiculture.data.repository.search.remote.service.SearchService
import javax.inject.Inject

class SearchRDS @Inject constructor(private val searchService: SearchService) : BaseRDS()