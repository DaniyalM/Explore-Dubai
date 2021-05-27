package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import com.app.dubaiculture.data.repository.event.local.models.Events
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attractions(
    val id: String,
    val title: String = "",
    val category: String = "",
    val IsFavourite: Boolean = false,
    var locationTitle: String? = "",
    var location: String? = "",
    var longitude: String? = "",
    var latitude: String? = "",
    var email: String? = "",
    var type : String?="",
    var number: String? = "",
    var audioLink: String? = "",
    var portraitImage: String? = "",
    var landscapeImage: String? = "",
    var description: String? = "",
    var summary: String? = "",
    var distance: Double = 0.0,
    var withinRadiusIconURL: String = "",
    var outOfRadiusIconURL: String = "",
    var startTime: String? = "",
    var endTime: String? = "",
    var endDay: String? = "",
    var startDay: String? = "",
    var color: String? = "",
    var emailContact : String? ="",
    var numberContact : String?="",
    var events: List<Events>? = null,
    var gallery: List<Gallery>? = null,
    var socialLink: List<SocialLink>? = null,
    var asset360: Assets360? = null,
    var siteMap: SiteMap? = null,
    var ibecons : Ibecons? =null,
    var withinRadiusIcon : String ? ="",
    var outOfRadiusIcon : String ? ="",
    ) : Parcelable
