package com.dubaiculture.data.repository.profile.local

import android.os.Parcelable
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.popular_service.local.models.EService
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favourite(
    val attractions: List<Attractions>,
    val events: List<Events>,
    val services: List<EService>,
) : Parcelable