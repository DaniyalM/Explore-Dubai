package com.dubaiculture.utils

import android.webkit.WebView
import com.dubaiculture.happiness.*
import java.util.*

object HappinessMeter {

    private val SECRET = "F0A7F2DDF3092D8D"
    private val SERVICE_PROVIDER = "Dubaiculture"
    private val CLIENT_ID = "dculbeatuser"


    fun load(type: Constants.TYPE, webView: WebView, culture: String) {
        // val webView = findViewById(R.id.webView) as WebView
        val secret: String = SECRET
        val serviceProvider: String = SERVICE_PROVIDER
        val clientID: String = CLIENT_ID
        val request = VotingRequest()
        val user = User()
        if (type == Constants.TYPE.TRANSACTION) {
            val transaction = Transaction()
            transaction.setGessEnabled("true")
            transaction.setNotes("MobileSDK Vote")
            transaction.setServiceDescription("Demo Transaction")
            transaction.setChannel("SMARTAPP")
            transaction.setServiceCode("2952")
            transaction.setTransactionID("Happiness Vote " + Date().time)
            request.setTransaction(transaction)
        } else {
            val application =
                Application(
                    "DubaiNow",
                    "http://mpay.qa.adeel.dubai.ae",
                    "SMARTAPP",
                    "ANDROID"
                )
            application.setNotes("MobileSDK Vote")
            request.setApplication(application)
        }
        val timeStamp: String = Utils.getUTCDate()
        val header = Header()
        header.setTimeStamp(timeStamp)
        header.setServiceProvider(serviceProvider)
        header.setThemeColor("#5E2E82")
        // Set MicroApp details
        if (type == Constants.TYPE.WITH_MICROAPP) {
            header.setMicroApp("General")
            header.setMicroAppDisplay("Micro App")
        }
        request.setHeader(header)
        request.setUser(user)
        /**
         * This is QA URL. Replace it with production once it is ready for production.
         */
        VotingManager.setHappinessUrl("https://happinessmeterqa.dubai.gov.ae/HappinessMeter2/MobilePostDataService")
        //For arabic pass lang "ar"
//        val lang: String
//        lang = if (checkbox.isChecked()) {
//            "ar"
//        } else {
//            "en"
//        }
//        binding.webView.setBackgroundColor(0);

        VotingManager.loadHappiness(
            webView,
            request,
            secret,
            serviceProvider,
            clientID,
            culture
        )
    }


}