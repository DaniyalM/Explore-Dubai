package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AttractionCategory(
    var id: String? = null,
    val title: String? = null,
    var icon: String? = null,
    val imgSelected: Int = 0,
    val imgUnSelected: Int = 0,
    var isSelected: Boolean = false,
    var selectedSvg: String? = null,
    var unselectedSvg: String? = null,
    var color: String? = null,
    var attractions: ArrayList<Attractions>? = null,
) : Parcelable


