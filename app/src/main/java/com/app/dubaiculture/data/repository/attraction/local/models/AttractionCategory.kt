package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionCategory(
    var id: String? = "",
    val title: String? = "",
    var icon: String? = "",
    val imgSelected: Int = 0,
    val imgUnSelected: Int = 0,
    var isSelected: Boolean = false,
    var selectedSvg: String? = "",
    var unselectedSvg: String? = "",
    var color: String? = "",
    var attractions: ArrayList<Attractions>? = null,
) : Parcelable


