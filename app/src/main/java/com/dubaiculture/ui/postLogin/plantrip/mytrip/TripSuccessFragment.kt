package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentTripSuccessBinding
import com.dubaiculture.happiness.*
import com.dubaiculture.ui.base.BaseDialogFragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import java.util.*


class TripSuccessFragment : BaseDialogFragment<FragmentTripSuccessBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()

    private val SECRET = "F0A7F2DDF3092D8D"
    private val SERVICE_PROVIDER = "Dubaiculture"
    private val CLIENT_ID = "dculbeatuser"
    private var currentType: Constants.TYPE = Constants.TYPE.WITH_MICROAPP

    override fun getTheme() = R.style.FullScreenDialog;

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripSuccessBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)
        lottieAnimationRTL(binding!!.animRegistration)

    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window!!.apply {
                setLayout(width, height)
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }

            }

        }
    }


    fun onOkClicked() {

        tripSharedViewModel._showPlan.value = Event(false)
        tripSharedViewModel._eventAttractionResponse.value = null
        tripSharedViewModel._eventAttractionList.value = null
        binding.webView.visibility = View.VISIBLE
        load(currentType)

//        navigateByDirections(TripSuccessFragmentDirections.actionTripSuccessToMySaveTripListing())

    }

    private fun load(type: Constants.TYPE) {
        currentType = type
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
                Application("DubaiNow", "http://mpay.qa.adeel.dubai.ae", "SMARTAPP", "ANDROID")
            application.setNotes("MobileSDK Vote")
            request.setApplication(application)
        }
        val timeStamp: String = Utils.getUTCDate()
        val header = Header()
        header.setTimeStamp(timeStamp)
        header.setServiceProvider(serviceProvider)
        header.setThemeColor("#5D2E82")
        // Set MicroApp details
        if (type == Constants.TYPE.WITH_MICROAPP) {
            header.setMicroApp("RTA")
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
        binding.webView.setBackgroundColor(Color.TRANSPARENT);

        VotingManager.loadHappiness(binding.webView, request, secret, serviceProvider, clientID, "en")
    }

}