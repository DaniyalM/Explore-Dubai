package com.dubaiculture.ui.postLogin.latestnews.newsfilter

data class SelectedFilter(
    var title: String,
    var isKeyword: Boolean = false,
    var isDateFrom: Boolean = false,
    var isDateTo: Boolean = false,
    var isTag: Boolean = false
)