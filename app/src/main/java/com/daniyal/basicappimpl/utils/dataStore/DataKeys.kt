package com.example.basearchitecture.common.Utils.dataStore

import androidx.datastore.preferences.preferencesKey

object DataKeys {
    val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL_KEY")
    val USER_NAME_KEY = preferencesKey<String>("USER_NAME_KEY")
    val USER_ID_KEY = preferencesKey<Int>("USER_ID_KEY")

}