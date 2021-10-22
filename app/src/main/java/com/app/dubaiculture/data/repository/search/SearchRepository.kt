package com.app.dubaiculture.data.repository.search

import com.app.dubaiculture.data.repository.base.BaseRepository
import com.app.dubaiculture.data.repository.search.remote.SearchRDS
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchRDS: SearchRDS,
) : BaseRepository()