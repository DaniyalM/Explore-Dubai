package com.app.dubaiculture.data.repository.filter.models

data class SelectedItems(
    val id : String? = "",
//    val title : String?=null

    val culture: String? = "",
//    val userId: String? = "",
    val category: String ? ="",
    val keyword: String? = "",
    val location: String? = "",
    val dateFrom: String? = "",
    val dateTo: String? = "",
    val type: String? = ""
)