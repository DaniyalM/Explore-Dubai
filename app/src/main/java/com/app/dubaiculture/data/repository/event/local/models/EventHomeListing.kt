package com.app.dubaiculture.data.repository.event.local.models


data class EventHomeListing(
    var title: String? = null,
    var category: String? = null,
    var events: List<Events>,
)


