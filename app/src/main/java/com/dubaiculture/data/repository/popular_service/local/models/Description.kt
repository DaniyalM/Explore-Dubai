package com.dubaiculture.data.repository.popular_service.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Description(
    val classification: String,
    val classificationTitle: String,
    val descriptions: String,
    val documentLink: String,
    val fileIcon: String,
    val fileName: String,
    val fileSize: String,
    val iD: String,
    val serviceChannelIcons: List<String>,
    val serviceChannelTitle: String,
    val title: String,
    val type: String,
    val typeTitle: String,
    val startServiceText: String,
    val startServiceUrl: String,
    val formName: String,
    val formSubmitURL: String
) : Parcelable
