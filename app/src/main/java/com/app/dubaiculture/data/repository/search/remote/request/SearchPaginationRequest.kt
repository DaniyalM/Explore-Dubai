package com.app.dubaiculture.data.repository.search.remote.request

data class SearchPaginationRequest(
    val keyword:String?="",
    val filter:String?="",
    val category:String?="",
    val culture:String?="",
    val sort:String?="",
    val isOld:Boolean?=false
)


//{
//    "PageNo": 0,
//    "PageSize": 0,
//    "Keyword": "string",
//    "Filter": "string",
//    "Category": "string",
//    "Culture": "string",
//    "Sort": "string",
//    "IsOld": true
//}