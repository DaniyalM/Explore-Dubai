package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter

data class Filter(
    var tags: MutableList<String> = mutableListOf(),
    var keyword: String = "",
    var dateFrom: String = "",
    var dateTo: String = "",
)