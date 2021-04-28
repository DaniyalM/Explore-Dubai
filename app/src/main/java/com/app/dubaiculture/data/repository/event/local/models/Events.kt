package com.app.dubaiculture.data.repository.event.local.models

import android.os.Parcelable
import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventSchedule
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Events(
    var id: String? = "",
    var title: String? = "",
    var category: String? = "",
    var desc: String? = "",
    var image: String? = "",
    var fromDate: String? = "",
    var fromMonthYear: String? = "",
    var fromTime: String? = "",
    var fromDay: String? = "",
    var toDate: String? = "",
    var toMonthYear: String? = "",
    var toTime: String? = "",
    var toDay: String? = "",
    var type: String? = "",
    var locationTitle: String? = "",
    var location: String? = "",
    var longitude: String? = "",
    var latitude: String? = "",
    var isFavourite: Boolean = false,
    var color: String? = null,
    var dateTo: String = "",
    var dateFrom: String = "",
    var distance: Double = 0.0,
    var currentLat: Double = 0.0,
    var currentLng: Double = 0.0,
    var emailContact : String? =null,
    var numberContact : String?=null,
    var socialLink: List<SocialLink>? = emptyList(),
    var eventSchedule: List<EventSchedule>? = emptyList(),
    var relatedEvents: List<Events>? = emptyList(),
) : Parcelable





