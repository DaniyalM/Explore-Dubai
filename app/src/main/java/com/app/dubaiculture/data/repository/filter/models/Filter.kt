package com.app.dubaiculture.data.repository.filter.models

import com.app.dubaiculture.data.repository.filter.Categories

data class Filter(
    val userID: String?= null,
    val eventKeyword: String?=null,
    val location: String?=null,
    val fromDate: String?=null,
    val toDate: String?=null,
    val type: String?=null,
    val culture : String?=null,
    val category: ArrayList<String>?=null
)

