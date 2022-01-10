package com.dubaiculture.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import com.dubaiculture.infrastructure.ApplicationEntry



object NetworkLiveData : LiveData<Boolean>() {
    private lateinit var application: ApplicationEntry
    private lateinit var networkRequest: NetworkRequest
    override fun onActive() {
        super.onActive()
        getDetails()
    }
    fun initNetwork(applicationEntry: ApplicationEntry) {
        this.application=applicationEntry
        networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }
    private fun getDetails() {
        try{
            val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    NetworkLiveData.postValue(true)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    NetworkLiveData.postValue(false)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    NetworkLiveData.postValue(false)
                }
            })
        }catch (e:Exception){
            e.stackTrace
        }
    }

     fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}

//https://medium.com/@iamsdt/observing-network-state-with-live-data-90167a8ba04d