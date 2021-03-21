package com.app.dubaiculture.data.repository.attraction.remote.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class SocialLinkDTO {
    @SerializedName("FacebookPageLink")
    @Expose
    var facebookPageLink: String = ""

    @SerializedName("FacebookIcon")
    @Expose
    var facebookIcon: String = ""

    @SerializedName("InstagramPageLink")
    @Expose
    var instagramPageLink: String = ""

    @SerializedName("InstagramIcon")
    @Expose
    var instagramIcon: String = ""
}