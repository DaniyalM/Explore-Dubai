package com.app.dubaiculture.data.repository.attraction.local.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SocialLink(
    var facebookPageLink: String = "",
    var facebookIcon: String = "",
    var instagramPageLink: String = "",
    var instagramIcon: String? = "",
    var twitterPageLink: String = "",
    var twitterIcon: String? = "",
    var youtubePageLink: String = "",
    var youtubeIcon: String? = "",
    var linkedInPageLink: String = "",
    var linkedInIcon: String? = "",
): Parcelable