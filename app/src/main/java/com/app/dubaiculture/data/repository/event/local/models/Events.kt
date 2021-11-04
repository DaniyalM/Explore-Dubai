
package com.app.dubaiculture.data.repository.event.local.models

import android.os.Parcelable
import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventSchedule
import kotlinx.parcelize.Parcelize

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
    var location: String,
    var longitude: String = "67.08119661055807",
    var latitude: String = "24.83250180519734",
    var isFavourite: Boolean = false,
    var isRegistered: Boolean = false,
    var IsSurveyed: Boolean = false,
    var color: String? = null,
    var dateTo: String,
    var dateFrom: String,
    var registrationDate: String,
    var isSurveySubmitted: Boolean = false,
    var distance: Double = 0.0,
    var currentLat: Double = 0.0,
    var currentLng: Double = 0.0,
    var emailContact: String? = null,
    var numberContact: String? = null,
    var socialLink: List<SocialLink>? = mutableListOf(),
    var eventSchedule: List<EventSchedule>? = mutableListOf(),
    var relatedEvents: List<Events>? = mutableListOf(),
) : Parcelable





