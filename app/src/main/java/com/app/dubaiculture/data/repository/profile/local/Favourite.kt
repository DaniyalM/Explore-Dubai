package com.app.dubaiculture.data.repository.profile.local

import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.event.local.models.Events

data class Favourite(val attractions: List<Attractions>, val events: List<Events>)