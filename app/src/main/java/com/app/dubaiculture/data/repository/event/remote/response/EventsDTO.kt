package com.app.dubaiculture.data.repository.event.remote.response

import android.os.Parcelable
import com.app.dubaiculture.data.repository.attraction.remote.response.SocialLinkDTO
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventsDTO(
    @SerializedName("ID")
    @Expose
    var id: String? = "",

    @SerializedName("Title")
    @Expose
    var title: String? = "",


    @SerializedName("Description")
    @Expose
    var desc: String? = "",


    @SerializedName("Category")
    @Expose
    var category: String? = "",

    @SerializedName("Image")
    @Expose
    var image: String? = "",

    @SerializedName("FromDate")
    @Expose
    var fromDate: String? = "",

    @SerializedName("FromMonthYear")
    @Expose
    var fromMonthYear: String? = "",

    @SerializedName("FromTime")
    @Expose
    var fromTime: String? = "",

    @SerializedName("FromDay")
    @Expose
    var fromDay: String? = "",

    @SerializedName("ToDate")
    @Expose
    var toDate: String? = "",

    @SerializedName("ToMonthYear")
    @Expose
    var toMonthYear: String? = "",

    @SerializedName("ToTime")
    @Expose
    var toTime: String? = "",

    @SerializedName("ToDay")
    @Expose
    var toDay: String? = "",

    @SerializedName("LocationTitle")
    @Expose
    var locationTitle: String? = "",
    @SerializedName("Location")
    @Expose
    var location: String? = "",

    @SerializedName("Type")
    @Expose
    var type: String? = "",

    @SerializedName("Latitude")
    @Expose
    var latitude: String = "24.83250180519734",

    @SerializedName("Longitude")
    @Expose
    var longitude: String = "67.08119661055807",

    @SerializedName("IsFavourite")
    @Expose
    var isFavourite: Boolean = false,
    @SerializedName("Color")
    @Expose
    var color: String? = null,
    @SerializedName("DateTo")
    @Expose
    var dateTo: String = "",
    @SerializedName("DateFrom")
    @Expose
    var dateFrom: String = "",


    @SerializedName("RegistrationDate")
    @Expose
    var registrationDate: String = "",


    @SerializedName("EmailContact")
    @Expose
    var emailContact: String = "",
    @SerializedName("NumberContact")
    @Expose
    var numberContact: String = "",


    @SerializedName("IsRegistered")
    @Expose
    var isRegistered: Boolean= false,
    @SerializedName("IsSurveyed")
    @Expose
    var isSurveyed: Boolean= false,


    @SerializedName("EventSchedule")
    @Expose
    val eventSchedule: List<EventScheduleDTO> = emptyList(),

    @SerializedName("RelatedEvents")
    @Expose
    var relatedEvents: List<EventsDTO> = emptyList(),
    @SerializedName("SocialLinks")
    @Expose
    var socialLinks: List<SocialLinkDTO>  ?= emptyList()

    ) : Parcelable

//"ID":"E333F68461E840928FE54225F93ECE3C",
//"Title":"The Definitive Guide \n to an Uncertain World 2",
//"Image":"/-/media/DC/Home-Modules/events-img-2.jpg",
//"FromDate":"01",
//"FromMonthYear":"Mar, 21",
//"FromTime":"05:18 PM",
//"FromDay":"Monday",
//"ToDate":"31 ",
//"ToMonthYear":"Mar, 21",
//"ToTime":"05:18 PM",
//"ToDay":"Wednesday",
//"Category":"Virtual Events",
//"LocationTitle":"Online",
//"Location":"https://www.google.com/maps/place/Burj+Khalifa/@25.075706,54.9468685,10z/data=!4m13!1m7!3m6!1s0x3e5f43496ad9c645:0xbde66e5084295162!2sDubai+-+United+Arab+Emirates!3b1!8m2!3d25.2048493!4d55.2707828!3m4!1s0x3e5f43348a67e24b:0xff45e502e1ceb7e2!8m2!3d25.197175!4d55.2743626",
//"Latitude":"",
//"Longitude":"",
//"Type":"Free",
//"Color":null,
//"DateTo":"2021-03-31T17:18:00",
//"DateFrom":"2021-03-01T17:18:00",
//"IsFavourite":false