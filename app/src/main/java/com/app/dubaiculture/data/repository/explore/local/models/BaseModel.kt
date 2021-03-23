package com.app.dubaiculture.data.repository.explore.local.models

import androidx.databinding.BindingAdapter
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.remote.response.AttractionDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.GalleryDTO
import com.app.dubaiculture.data.repository.attraction.remote.response.SocialLinkDTO
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rishabhharit.roundedimageview.RoundedImageView

class BaseModel {
//    val imgSelected: String=""
//    val imgUnSelected:String=""

    @SerializedName("ID")
    @Expose
    var id: String? = null

    @SerializedName("Icon")
    @Expose
    var icon: String? = null


//    @SerializedName("WhiteIcon")
//    @Expose
//    var whiteIcon: String? = null

    @SerializedName("Title")
    @Expose
    var title: String? = null

    @SerializedName("Category")
    @Expose
    var category: String? = null

    @SerializedName("LocationTitle")
    @Expose
    var locationTitle: String? = ""

    @SerializedName("Location")
    @Expose
    var location: String? = null

    @SerializedName("PortraitImage")
    @Expose
    var portraitImage: String? = null

    @SerializedName("LandscapeImage")
    @Expose
    var landscapeImage: String? = null

    @SerializedName("Description")
    @Expose
    var description: String? = null

    @SerializedName("StartTime")
    @Expose
    var startTime: String? = null

    @SerializedName("EndTime")
    @Expose
    var endTime: String? = null

    @SerializedName("EndDay")
    @Expose
    var endDay: String? = null
    @SerializedName("StartDay")
    @Expose
    var startDay: String? = null

    @SerializedName("Image")
    @Expose
    var image: String? = null

    @SerializedName("Color")
    @Expose
    var color: String? = null

    @SerializedName("FromDate")
    @Expose
    var fromDate: String? = null

    @SerializedName("FromMonthYear")
    @Expose
    var fromMonthYear: String? = null

    @SerializedName("FromTime")
    @Expose
    var fromTime: String? = null

    @SerializedName("FromDay")
    @Expose
    var fromDay: String? = null

    @SerializedName("ToDate")
    @Expose
    var toDate: String? = null

    @SerializedName("ToMonthYear")
    @Expose
    var toMonthYear: String? = null

    @SerializedName("ToTime")
    @Expose
    var toTime: String? = null

    @SerializedName("ToDay")
    @Expose
    var toDay: String? = null

    @SerializedName("Type")
    @Expose
    var type: String? = null

    @SerializedName("PostedDate")
    @Expose
    var postedDate: String? = null

    @SerializedName("Date")
    @Expose
    var date: String? = null


    @SerializedName("ColoredIcon")
    @Expose
    var coloredIcon: String? = ""

    @SerializedName("JsonFile")
    @Expose
    var jsonFile: String? =""

    @SerializedName("HoveredJsonFile")
    @Expose
    var hoveredJsonFile: String? = ""

    @SerializedName("PlayJson")
    @Expose
    var playJson: Boolean? = false

    @SerializedName("IsFavourite")
    @Expose
    var isFavourite: Boolean = false
    @SerializedName("AudioLink")
    @Expose
    var audioLink: String? = ""
    @SerializedName("EmailContact")
    @Expose
    var emailContact: String? = ""
    @SerializedName("NumberContact")
    @Expose
    var numberContact: String? = ""
    @SerializedName("Latitude")
    @Expose
    var latitude: String? = ""
    @SerializedName("Longitude")
    @Expose
    var longitude: String? = ""

    @SerializedName("Events")
    @Expose
    var events: List<EventsDTO>  ?= null

    @SerializedName("SocialLinks")
    @Expose
    var socialLinks: List<SocialLinkDTO>  ?= null

    @SerializedName("Gallery")
    @Expose
    var gallery: List<GalleryDTO> ?= null

    @SerializedName("WhiteIcon")
    @Expose
    var selectedSvg: String? = null

    @SerializedName("NonWhiteIcon")
    @Expose
    var unselectedSvg: String? = icon

    @SerializedName("Attractions")
    @Expose
    var attractions: ArrayList<AttractionDTO>? =null


}
