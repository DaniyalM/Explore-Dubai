package com.app.dubaiculture.utils

object Constants {
    object Alert {
        const val DEFAULT_TITLE: String = "Alert"
        const val DEFAULT_TEXT_POSITIVE: String = "OK"
        const val TEXT_POSITIVE: String = "YES"
        const val TEXT_NEGATIVE: String = "NO"
    }

    object Error {
        const val SOMETHING_WENT_WRONG: String = "Something went wrong"
        const val SELECT_COUNTRY: String = "Select country first"
        const val INTERNET_CONNECTION_ERROR: String = "Internet Connection Error"

    }

    object ResendEmail {
        const val RESEND_TIMER: Long = 30000
        const val INTERVAL: Long = 1000
        const val WAIT_FOR_SECONDS: String = "Please wait for "
    }

    object Location {
        const val ACTION_LOCATION_PROVIDER_CHANGED: String = "android.location.PROVIDERS_CHANGED"
        const val ENABLE_LOCATION: String =
            "Looks like location is disabled in device settings. Do you want to enable it?"
    }

    object NavResults {
        const val DO_ANIMATION: String = "doAnim"
        const val SELECTED_CITY: String = "selectedCity"
    }

    object NavBundles {
        const val COMES_FROM_LOGIN: String = "loginFragment"
        const val COMES_FROM_POST_LOGIN: String = "postloginFragment"
        const val SELECTED_CITY: String = "selectedCity"
        const val SORTED_LIST: String = "SORTED_EVENT_LIST"
        const val EVENT_OBJECT: String = "eventObject"
        const val ATTRACTION_OBJECT: String = "attractionObject"
        const val ATTRACTION_CAT_OBJECT: String = "attractionCatObject"
        const val ATTRACTION_GALLERY_LIST: String = "attractionGalleryList"
        const val THREESIXTY_GALLERY_LIST: String = "THREE60_GALLERY_LIST"
        const val EXPLORE_MAP_LIST: String = "explore_map_list"
        const val EVENT_MAP_LIST: String = "event_map_list"
        const val ATTRACTION_ID: String = "attraction_id"
        const val CATEGORY: String = "category"
        const val BECON_LIST: String = "becon_list"
        const val BECON_OBJECT: String = "becon_object"
        const val SITE_MAP_OBJ: String = "siteMap_obj"
        const val LOCATION_LAT: String = "location_lat"
        const val LOCATION_LNG: String = "location_lng"
        const val META_DATA_ID: String = "meta_data_id"
        const val IMAGES_LIST: String = "images_list"


    }
    object IBecons {
        const val IDENTIFIER: String = "com.flagship.dubaiculture"
        const val UUID_BECON: String = "B9407F30-F5F8-466E-AFF9-25556B57FE6D"

    }

    object Links {
        const val TERMS_CONDITIONS: String = "https://uat.weareneomads.com/terms-of-use"
    }
    object StaticLatLng{
        const val LAT =  24.8623
        const val LNG =  67.0627
    }

    object Categories{
        const val MUSEUMS = "Museums"
        const val HERITAGE_SITES = "Heritage Sites"
        const val ART_GALLERY = "Art Galleries"
        const val FESTIVALS = "Festivals"
        const val LIBRARIES = "Libraries"

    }
    object Colors {
        const val WHITE: String = "#ffffff"
        const val SEE_MORE_BLUE: String = "#103667"
        const val LINK: String = "#267FB7"
    }
    object AR {
        const val CLIENT_TOKEN: String = "2e7e1551d007a8adc3346fa48f07b2c7"
        const val TARGET_ID: String = "60860b2e7c633e71c8d1e5cb"
    }
}