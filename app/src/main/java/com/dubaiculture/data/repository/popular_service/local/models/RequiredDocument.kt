package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequiredDocument(
    val requiredDocuments: List<String>,
    val requiredDocumentsTitle: String
):Parcelable