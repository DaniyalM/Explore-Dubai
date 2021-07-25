package com.app.dubaiculture.data.repository.popular_service.remote.response

data class DescriptionDTO(
    val Classification: String,
    val ClassificationTitle: String,
    val Descriptions: String,
    val DocumentLink: String,
    val FileIcon: String,
    val FileName: String,
    val FileSize: String,
    val ID: String,
    val ServiceChannelIcons: List<String>,
    val ServiceChannelTitle: String,
    val Title: String,
    val Type: String,
    val TypeTitle: String
)