package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EService(
    val category: String,
    val categoryId: String,
    val id: String,
    val summary: String,
    val title: String,
    val startServiceUrl: String
) : Parcelable