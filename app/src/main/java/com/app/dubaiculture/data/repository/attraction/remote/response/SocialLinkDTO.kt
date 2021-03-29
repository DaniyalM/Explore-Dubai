package com.app.dubaiculture.data.repository.attraction.remote.response

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SocialLinkDTO : Parcelable {
    @SerializedName("FacebookPageLink")
    @Expose
    var facebookPageLink: String? = ""

    @SerializedName("FacebookIcon")
    @Expose
    var facebookIcon: String? = ""

    @SerializedName("InstagramPageLink")
    @Expose
    var instagramPageLink: String? = ""

    @SerializedName("InstagramIcon")
    @Expose
    var instagramIcon: String? = ""

    @SerializedName("TwitterPageLink")
    @Expose
    var twitterPageLink: String = ""

    @SerializedName("TwitterIcon")
    @Expose
    var twitterIcon: String? = ""

    @SerializedName("YouTubePageLink")
    @Expose
    var youtubePageLink: String = ""

    @SerializedName("YouTubeIcon")
    @Expose
    var youtubeIcon: String? = ""

    @SerializedName("LinkedInPageLink")
    @Expose
    var linkedInPageLink: String = ""

    @SerializedName("LinkedInIcon")
    @Expose
    var linkedInIcon: String? = ""

}