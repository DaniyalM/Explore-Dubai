package com.dubaiculture.data.repository.viewgallery.local

data class ViewGalleryModel(

    val id : String? =null,
    val title : String?=null,
    val desc : String?=null,
    val images : List<Images>? = emptyList()
)