package com.app.dubaiculture.data.repository.event.local.models

import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents


data class EventHomeListing(
    var category: String? = null,
    var events: List<UpComingEvents>,
)


