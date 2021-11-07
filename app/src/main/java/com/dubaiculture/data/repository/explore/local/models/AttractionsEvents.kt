package com.dubaiculture.data.repository.explore.local.models

import com.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.event.local.models.Filter
import com.dubaiculture.data.repository.event.remote.response.EventsDTO

data class AttractionsEvents (
        var attractionCategory: ArrayList<AttractionCategory>?=null,
        var events: ArrayList<Events>?=null,
        )