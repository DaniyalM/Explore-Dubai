package com.app.dubalculture.utils.dataStore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import com.app.dubalculture.utils.security.lds.SecurityManagerLDS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.lang.IllegalArgumentException
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    @ApplicationContext context: Context,
    private val securityManager: SecurityManagerLDS
) {

    // Create a Preferences DataStore
    private val dataStore: DataStore<Preferences> by lazy {
        context.createDataStore(
            name = "my_app_data_store"
        )
    }

    suspend fun <T> setData(key: Preferences.Key<String>, data: T) {
        val cipher = securityManager.encrypt(toString(data))
        Timber.e(cipher)
        dataStore.edit {
            it[key] = cipher
        }
    }

    suspend fun getInt(key: Preferences.Key<String>, defaultValue: Int? = null): Int? {
        val x = dataStore.data.map {
            it[key]
        }.firstOrNull()
        return when (x) {
            null -> defaultValue
            else -> {
                securityManager.decrypt(x).toIntOrNull()
            }
        }
    }

    suspend fun getString(key: Preferences.Key<String>, defaultValue: String? = null): String? {
        val x = dataStore.data.map {
            it[key]
        }.firstOrNull()
        return when (x) {
            null -> defaultValue
            else -> {
                securityManager.decrypt(x)
            }
        }
    }

    suspend fun getFloat(key: Preferences.Key<String>, defaultValue: Float? = null): Float? {
        val x = dataStore.data.map {
            it[key]
        }.firstOrNull()
        return when (x) {
            null -> defaultValue
            else -> {
                securityManager.decrypt(x).toFloatOrNull()
            }
        }
    }

    suspend fun getBoolean(key: Preferences.Key<String>, defaultValue: Boolean? = null): Boolean? {
        val x = dataStore.data.map {
            it[key]
        }.firstOrNull()
        return when (x) {
            null -> defaultValue
            else -> {
                securityManager.decrypt(x).toBoolean()
            }
        }
    }

    suspend fun getLong(key: Preferences.Key<String>, defaultValue: Long? = null): Long? {
        val x = dataStore.data.map {
            it[key]
        }.firstOrNull()
        return when (x) {
            null -> defaultValue
            else -> {
                securityManager.decrypt(x).toLongOrNull()
            }
        }
    }

    //            is Long, Int, String, Boolean, Float -> value.toString() doesnot work, TODO verify
    private fun <T> toString(value: T): String {
        return when (value) {
            is Long -> value.toString()
            is Float -> value.toString()
            is Boolean -> value.toString()
            is String -> value.toString()
            is Int -> value.toString()
            else -> throw IllegalArgumentException("Type not supported")
        }
    }

}
//https://proandroiddev.com/lets-explore-jetpack-datastore-in-android-621f3564b57