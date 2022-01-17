package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Procedure(
    val serviceProcedure: List<ServiceProcedure>,
    val serviceProcedureTitle: String
):Parcelable