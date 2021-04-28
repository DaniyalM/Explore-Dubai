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
        const val SELECTED_CITY: String = "selectedCity"
    }


    object Links {
        const val TERMS_CONDITIONS: String = "https://uat.weareneomads.com/terms-of-use"
    }

}