package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceProcedure(
    val summary: String,
    val title: String,
    var id:Int?
):Parcelable