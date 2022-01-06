package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import com.dubaiculture.data.repository.more.local.FaqItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class FAQ(
    val fAQs: List<FaqItem>,
    val fAQsTitle: String
):Parcelable