package com.dubaiculture.utils.dataStore

import androidx.datastore.preferences.preferencesKey
import com.dubaiculture.data.repository.user.local.User

object DataKeys {
    val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL_KEY")
    val USER_NAME_KEY = preferencesKey<String>("USER_NAME_KEY")
    val USER_ID_KEY = preferencesKey<Int>("USER_ID_KEY")
    val ONBOARDING_ID_KEY = preferencesKey<String>("ONBOARDING_ID_KEY")
    val USER_SESSION = preferencesKey<String>("USER_SESSION_KEY")


}