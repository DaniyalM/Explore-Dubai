package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentTripSuccessBinding
import com.dubaiculture.ui.base.BaseDialogFragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.happiness.HappinessMeter
import com.dubaiculture.happiness.Type
import com.dubaiculture.utils.event.Event
import com.dubaiculture.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripSuccessFragment : BaseDialogFragment<FragmentTripSuccessBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


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

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {

                if (url.contains("happiness://done")) {
                    navigateByDirections(TripSuccessFragmentDirections.actionTripSuccessToMySaveTripListing())
                }

            }
        }

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
//        binding.flWebview.show()
//        //    load(currentType)
//        HappinessMeter.load(
//            Type.WITHOUT_MICROAPP,
//            binding.webView,
//            application.auth.locale.toString()
//        )

        navigateByDirections(TripSuccessFragmentDirections.actionTripSuccessToMySaveTripListing())

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.isCancelable = false
        return super.onCreateDialog(savedInstanceState)

    }


}