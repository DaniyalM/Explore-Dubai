package com.app.dubaiculture.data.repository.attraction.local.models


data class AttractionCategory(
    var id: String? = null,
    val title: String? = null,
    var icon: String? = null,
    val imgSelected: Int = 0,
    val imgUnSelected: Int = 0,
    var isSelected: Boolean = false,
    var selectedSvg: String? = null,
    var unselectedSvg: String? = null,
)


