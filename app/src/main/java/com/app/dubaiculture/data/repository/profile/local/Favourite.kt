package com.app.dubaiculture.data.repository.profile.local

import android.os.Parcelable
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.popular_service.local.models.EService
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favourite(
    val attractions: List<Attractions>,
    val events: List<Events>,
    val services: List<EService>,
) : Parcelable