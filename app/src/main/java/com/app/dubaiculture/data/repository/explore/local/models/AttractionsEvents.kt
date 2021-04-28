package com.app.dubaiculture.data.repository.explore.local.models

import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO

data class AttractionsEvents (
        var attractionCategory: ArrayList<AttractionCategory>?=null,
        var events: ArrayList<Events>?=null,
        )