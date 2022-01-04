package com.dubaiculture.data.repository.more.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FaqItem(
    val id:Int?,
    val answer: String,
    val question: String,
    val count: Int?= 0,
    val is_expanded: Boolean = false
):Parcelable